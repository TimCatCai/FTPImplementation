package server;

import process.*;
import reposity.network.NetworkManager;
import reposity.network.ProviderNetworkManagerImp;
import server.Commands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ServerProcessThread implements Runnable , ServerProcessable {
    private Socket socketToUser;
    private DataTransferProcessable commandsTransferProcess;
    private CommandManagerable commandManager;
    private ServerProcessable PIInterface;
    private EventQueue serverProcessThreadEventQueue;
    private  AbstractCommand commandReceived;

    private static int SERVER_PI_DEFAULT_PORT = 21;

    public ServerProcessThread(Socket socketToUser) {
        this.socketToUser = socketToUser;
        NetworkManager serverPINetworkManager = new ProviderNetworkManagerImp(SERVER_PI_DEFAULT_PORT);
        PIInterface = new ProtocolInterperImp();
        commandsTransferProcess = new DataTransferProcess(serverPINetworkManager, PIInterface);
        commandManager = new CommandManager();

        serverProcessThreadEventQueue = new EventQueue();
    }

    @Override
    public void run() {
        Reply tempReply;
        while (!socketToUser.isClosed()) {
            commandReceived = acceptCommand();
            tempReply = commandManager.parse(commandReceived);
            sentReply(tempReply);
        }

        try {
            socketToUser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void replyOperationResult(Event response) {
        serverProcessThreadEventQueue.addEvent(response);
    }

    @Override
    public void newCommandReady(Event newCommand) {
        serverProcessThreadEventQueue.addEvent(newCommand);
    }
    private AbstractCommand acceptCommand() {
        Event stringEvent = new StringEvent("", DataDirection.RECEIVE);
        int commandStateCode;
        this.commandsTransferProcess.receiveData(stringEvent);
        stringEvent = serverProcessThreadEventQueue.takeEvent();
        while (stringEvent.getDirection() == DataDirection.OWN){

            if(stringEvent instanceof FileEvent || stringEvent instanceof StringEvent){
                commandStateCode = commandReceived.getSuccessCode();
            }else{
                commandStateCode = commandReceived.getFailureCode();
            }
            sentReply(ReplyRepo.getReply(commandStateCode));

            stringEvent =  serverProcessThreadEventQueue.takeEvent();
        }

        return spiltNewCommand(stringEvent.getData());
    }

    private AbstractCommand spiltNewCommand(String data){
        String [] commandString = data.split("|");
        String [] parameters = null;
        if(commandString.length >= 2){
            parameters = Arrays.copyOfRange(commandString,1,commandString.length);
        }
        AbstractCommand newCommand = new USER(commandString[0],
                "",
                commandString.length - 1,
                parameters);
        return newCommand;
    }
    private void sentReply(Reply reply){
        StringEvent tempEvent;
        tempEvent = new StringEvent(reply.toString(), DataDirection.SENT);
        commandsTransferProcess.sentData(tempEvent);
    }
}
