package server;

import client.UserState;
import client.UserStateable;
import process.*;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;
import server.Commands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerProcessThread implements Runnable , ManagerProcessable {
    private Socket socketToUser;
    private DataTransferProcessable serverCommandsTransferProcess;
    private DataTransferProcessable serverDataTransferProcess;
    private CommandManagerable commandManager;

    private EventQueue serverProcessThreadEventQueue;



    private  AbstractCommand commandReceived;
    private static int USER_DTP_DEFAULT_PORT = 20;

    private final String SERVER_COMMANDS_TRANSFER_PROCESS_ID = "commands_transfer_process_id";
    private final String SERVER_DATA_TRANSFER_PROCESS_ID = "server_data_transfer_process_id";
    private final String SERVER_PROCESS_THREAD_ID = "server_process_thread_id";
    private Logger logger= Logger.getLogger(ServerProcessThread.class.getName());

    public ServerProcessThread(Socket socketToUser) {
        this.socketToUser = socketToUser;
        ProviderNetworkManagerImp serverPINetworkManager = new ProviderNetworkManagerImp(socketToUser);
        serverCommandsTransferProcess = new DataTransferProcess(serverPINetworkManager,this, SERVER_COMMANDS_TRANSFER_PROCESS_ID);
        ConnectorNetworkManagerImp serverDTPNetworkManager = new ConnectorNetworkManagerImp(
                socketToUser.getInetAddress(),
                USER_DTP_DEFAULT_PORT
                );
        serverDataTransferProcess = new DataTransferProcess(serverDTPNetworkManager,this,
                SERVER_DATA_TRANSFER_PROCESS_ID);
        String hostName = new String(socketToUser.getInetAddress().getAddress());
        UserStateable userState = new UserState(hostName,USER_DTP_DEFAULT_PORT,null);
        commandManager = new CommandManager(userState);

        serverProcessThreadEventQueue = new EventQueue();
    }

    @Override
    public void run() {
        Reply replyForCommandOperation;
        CommandExecuteResult commandExecuteResult;
        while (!socketToUser.isClosed()) {
            commandReceived = acceptCommandFromCommandTransferProcess();

            commandExecuteResult = commandManager.parse(commandReceived);
            replyForCommandOperation = commandExecuteResult.getReplyForCommand();
            logger.info("command accepted: " + commandReceived.getName()
                    + "\nreplyForCommandOperation content:" + replyForCommandOperation.toString() );
            sentReply(replyForCommandOperation);

            Event operation = commandExecuteResult.getOperation();
           if(operation != null){
               if(operation.getDirection() == DataDirection.RECEIVE){
                   serverDataTransferProcess.receiveData(operation);
               }else if(operation.getDirection() == DataDirection.SENT){
                   serverDataTransferProcess.sentData(operation);
               }
           }


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
        this.serverCommandsTransferProcess.receiveData(desiredCommandEvent);
        newEventFromEventQueue = serverProcessThreadEventQueue.takeEvent();

        while (! newEventFromEventQueue.getOriginal().equals(SERVER_COMMANDS_TRANSFER_PROCESS_ID)){

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
       logger.info("desiredCommandEvent content:" + desiredCommandEvent.getData());
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

        for(String command :commandString){
            System.out.print(command + " " );

        }
        System.out.println();

        AbstractCommand newCommand = new USER(commandString[0].toUpperCase(),
                "",
                commandString.length - 1,
                parameters);
        return newCommand;
    }

    private void sentReply(Reply reply){
        StringEvent replyForUserCommandEvent;
        replyForUserCommandEvent = new StringEvent(reply.toString(), DataDirection.SENT,SERVER_PROCESS_THREAD_ID);
        serverCommandsTransferProcess.sentData(replyForUserCommandEvent);
    }


}
