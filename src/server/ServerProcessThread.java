package server;

import client.UserState;
import client.UserStateable;
import process.*;
import process.event.Event;
import process.event.EventQueue;
import process.event.ReplyEvent;
import process.event.StringEvent;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;
import server.commands.*;
import server.commands.definition.AbstractCommand;
import server.commands.manager.CommandManager;
import server.commands.manager.CommandManagerable;
import utils.data.transmission.CommandTransmission;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerProcessThread implements Runnable , ManagerProcessable {
    private Socket socketToUser;
    private DataTransferProcessable serverCommandsTransferProcess;
    private DataTransferProcessable serverDataTransferProcess;
    private CommandManagerable commandManager;

    private EventQueue serverProcessThreadEventQueue;



    private AbstractCommand commandReceived;
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
            sentReply(replyForCommandOperation);
            Event eventForSendingReply = serverProcessThreadEventQueue .takeEvent();
            logger.info("The state of sending reply to user"+eventForSendingReply.getData());

            Event operation = commandExecuteResult.getOperation();
            Event newEventFromEventQueueForOperationResult;
           if(operation != null){

               if(operation.getDirection() == DataDirection.RECEIVE){
                   logger.info("The content of operation to Server_DTP is " + operation.getData());
                   serverDataTransferProcess.receiveData(operation);
               }else if(operation.getDirection() == DataDirection.SENT){
                   serverDataTransferProcess.sentData(operation);
                   logger.info("The content of sent operation to Server_DTP is " + operation.getData());
               }

               newEventFromEventQueueForOperationResult = serverProcessThreadEventQueue.takeEvent();
               if(newEventFromEventQueueForOperationResult.getOriginal() == SERVER_DATA_TRANSFER_PROCESS_ID){
                   logger.info("command operation data sending result: " + newEventFromEventQueueForOperationResult.getData());
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

        while (! newEventFromEventQueue.getOriginal().equals(SERVER_COMMANDS_TRANSFER_PROCESS_ID)
                || newEventFromEventQueue.getDirection() == DataDirection.SENT){

            if(newEventFromEventQueue instanceof StringEvent){

            }

            else if(newEventFromEventQueue instanceof ReplyEvent){
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
        return CommandTransmission.spiltNewCommandStringToCommand(desiredCommandEvent.getData(),"\\|",logger);
    }



    private void sentReply(Reply reply){
        if(reply != null){
            StringEvent replyForUserCommandEvent;
            replyForUserCommandEvent = new StringEvent(reply.toString(), DataDirection.SENT,SERVER_PROCESS_THREAD_ID);
            serverCommandsTransferProcess.sentData(replyForUserCommandEvent);
        }
    }


}
