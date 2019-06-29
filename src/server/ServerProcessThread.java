package server;

import process.*;
import reposity.network.ProviderNetworkManagerImp;
import server.Commands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerProcessThread implements Runnable , ManagerProcessable {
    private Socket socketToUser;
    private DataTransferProcessable commandsTransferProcess;
    private CommandManagerable commandManager;
    private EventQueue serverProcessThreadEventQueue;
    private  AbstractCommand commandReceived;
    private static int SERVER_PI_DEFAULT_PORT = 58888;

    private final String COMMANDS_TRANSFER_PROCESS_ID = "commands_transfer_process_id";
    private final String SERVER_PROCESS_THREAD_ID = "server_process_thread_id";
    private Logger logger= Logger.getLogger(ServerProcessThread.class.getName());

    public ServerProcessThread(Socket socketToUser) {
        this.socketToUser = socketToUser;
        ProviderNetworkManagerImp serverPINetworkManager = new ProviderNetworkManagerImp(socketToUser);
        commandsTransferProcess = new DataTransferProcess(serverPINetworkManager,this, COMMANDS_TRANSFER_PROCESS_ID);
        commandManager = new CommandManager();
        serverProcessThreadEventQueue = new EventQueue();
    }

    @Override
    public void run() {
        Reply replyForCommandOperation;
        while (!socketToUser.isClosed()) {
            commandReceived = acceptCommandFromCommandTransferProcess();

            replyForCommandOperation = commandManager.parse(commandReceived);
            logger.info("command accepted: " + commandReceived.getName()
                    + "\nreplyForCommandOperation content:" + replyForCommandOperation.toString() );

            sentReply(replyForCommandOperation);
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


    private AbstractCommand acceptCommandFromCommandTransferProcess() {
        StringEvent desiredCommandEvent = new StringEvent("", DataDirection.RECEIVE,SERVER_PROCESS_THREAD_ID);
        Event newEventFromEventQueue;
        Reply replyToUserForFileTransferState ;
        this.commandsTransferProcess.receiveData(desiredCommandEvent);
        newEventFromEventQueue = serverProcessThreadEventQueue.takeEvent();

        while (! newEventFromEventQueue.getOriginal().equals(COMMANDS_TRANSFER_PROCESS_ID)){

            if(newEventFromEventQueue instanceof StringEvent){

            }else if(newEventFromEventQueue instanceof ReplyEvent){
                /*
                    这里不对从数据传送层（ServerDataTransferProcess)收到的ReplyEvent进行任何处理，
                    直接回复给User端。本来是应该对收到的ReplyEvent进行解析之后，得到对应的状态码及
                    描述，之后给予User端应答，这里简化处理
                 */
                replyToUserForFileTransferState = new Reply(0,newEventFromEventQueue.getData());
                sentReply(replyToUserForFileTransferState);
            }
            newEventFromEventQueue =  serverProcessThreadEventQueue.takeEvent();
        }

        desiredCommandEvent = (StringEvent) newEventFromEventQueue;
       logger.info("in acceptCommandFromCommandTransferProcess " + "desiredCommandEvent content:"
                + desiredCommandEvent.getData());
        return spiltNewCommand(desiredCommandEvent.getData());
    }

    private AbstractCommand spiltNewCommand(String data){
        String [] commandString = data.split("\\|");
        String [] parameters = null;

        if(commandString.length >= 2){
            parameters = Arrays.copyOfRange(commandString,1,commandString.length);
        }else{
            parameters = new String [1];
            parameters[0] = commandString[0];
        }

        logger.info(" in spiltNewCommand commandString length: " + commandString.length);
        System.out.print("its content is: ");

        for(String command :commandString){
            System.out.print(command + " " );

        }
        System.out.println();

        AbstractCommand newCommand = new USER(commandString[0],
                "",
                commandString.length - 1,
                parameters);
        return newCommand;
    }

    private void sentReply(Reply reply){
        StringEvent replyForUserCommandEvent;
        replyForUserCommandEvent = new StringEvent(reply.toString(), DataDirection.SENT,SERVER_PROCESS_THREAD_ID);
        commandsTransferProcess.sentData(replyForUserCommandEvent);
    }
}
