package client;

import process.*;
import process.event.*;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;
import server.commands.CommandsRepo;
import server.commands.Reply;
import server.commands.definition.AbstractCommand;
import server.commands.definition.LIST;
import server.commands.definition.RETR;
import utils.data.transmission.CommandTransmission;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;


public class Controller implements UserManagerProccessable {
    private Path DEFAULT_USER_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    private EventQueue clientEventQueue;
    private String hostName;
    private int port;
    private DataTransferProcessable userPI;
    private DataTransferProcessable userDTP;
    private ViewInterface view;

    private final int DEFAULT_SERVER_PORT = 21;
    private final int DEFAULT_USER_DATA_PORT = 20;


    private final String USER_PI_ID = "user_pi";
    private final String USER_DTP_ID = "user_dtp";

    private final String USER_PROCESS_THREAD_CONTROLLER_ID = "user_process_thread_controller";
//    private Logger logger = Logger.getLogger(Controller.class.getName());

    public Controller(ViewInterface view) {
        this.view = view;
        clientEventQueue = new EventQueue();
    }

    public void start(String path) {
//        hostName = view.read("Hostname");
//        port = Integer.parseInt(view.read("port"));
        if(path.length() != 0){
            DEFAULT_USER_DIRECTORY = Paths.get(path);
        }
        hostName = "127.0.0.1";
        userPI = new DataTransferProcess(new ConnectorNetworkManagerImp(hostName, DEFAULT_SERVER_PORT),
                this,
                USER_PI_ID
        );
        userDTP = new DataTransferProcess(new ProviderNetworkManagerImp(DEFAULT_USER_DATA_PORT),
                this,
                USER_DTP_ID
        );
        Event newEventFromClientEventQueue;
        String commandInputString;
        Scanner scanner = new Scanner(System.in);
        StringBuilder commandWillSent;
        while (true) {
            commandInputString = scanner.nextLine();
            if (!isCommandInputValid(commandInputString)) {
                System.out.println("Input command please!");
                continue;
            }

            String[] commandInput = commandInputString.split(" ");
            commandWillSent = new StringBuilder();
            for (int i = 0; i < commandInput.length - 1; i++) {
                commandWillSent.append(commandInput[i]);
                //add delimiter for each one
                commandWillSent.append("|");
            }
            //The last one has no delimiter
            commandWillSent.append(commandInput[commandInput.length - 1]);
//            logger.info(commandWillSent.toString());

            userPI.sentData(new StringEvent(commandWillSent.toString(), DataDirection.SENT,
                    USER_PROCESS_THREAD_CONTROLLER_ID));

            // flit sending command result
            // waiting for sending result
            newEventFromClientEventQueue = clientEventQueue.takeEvent();
            if (USER_PI_ID.equals(newEventFromClientEventQueue.getOriginal())) {
                view.display(newEventFromClientEventQueue.getData());
            }

            //notify USR_PI to begin to read reply from server
            userPI.receiveData(new StringEvent("", DataDirection.RECEIVE,
                    USER_PROCESS_THREAD_CONTROLLER_ID));
            //waiting for reply
            newEventFromClientEventQueue = clientEventQueue.takeEvent();

//            logger.info("data accept from user_Pi:" + newEventFromClientEventQueue.getDirection());

            // reply is accepted
            Reply commandReply = null;
            // event comes from user protocol interpreter process
//            if (USER_PI_ID.equals(newEventFromClientEventQueue.getOriginal())) {
                String[] commandReplyStrings = CommandTransmission
                        .splitCommandString(newEventFromClientEventQueue.getData(),"\\|");

                if (commandReplyStrings != null) {
//                    logger.info("accepted reply state code: " + commandReplyStrings[0]);
                    commandReply = new Reply(Integer.parseInt(commandReplyStrings[0]), commandReplyStrings[1]);
                    view.display(newEventFromClientEventQueue.getData());
                }
//            }


            Event newEventForOperation;
            // check whether data operation is need or not
            if ((newEventForOperation = hasDataOperation(commandInputString, commandReply)) != null) {
                if (newEventForOperation.getDirection() == DataDirection.SENT) {
                    userDTP.sentData(newEventForOperation);
                } else {
                    userDTP.receiveData(newEventForOperation);
                }

                newEventFromClientEventQueue = clientEventQueue.takeEvent();
                /*
                    get reply from server, do some operations for reply
                    always waiting for getting reply for data operation from DTP
                */
//                while (USER_PI_ID.equals(newEventFromClientEventQueue.getOriginal())) {
//                    logger.info("new reply from server is: " + newEventFromClientEventQueue.getData());
//                    if (newEventFromClientEventQueue.getData() != null) {
//                        view.display(newEventFromClientEventQueue.getData());
//                    }
//                    newEventFromClientEventQueue = clientEventQueue.takeEvent();
//                }

                // get data transfer operation result and just display it;
//                logger.info("the data receiving or sending result from DTP:" + newEventFromClientEventQueue.getData());
                view.display(newEventFromClientEventQueue.getData());

            }


        }
    }

    @Override
    public void readCommandFromConsole(Event event) {
        clientEventQueue.addEvent(event);
    }

    @Override
    public void replyOperationResult(Event response) {
        clientEventQueue.addEvent(response);
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    private boolean isCommandInputValid(String command) {
        return command.length() != 0;
    }

    private Event hasDataOperation(String commandString, Reply commandReply) {
        Event dataEvent = null;
        AbstractCommand newCommand = CommandTransmission.spiltNewCommandStringToCommand(commandString," ",null);//logger);

        String LISTCommandName = CommandsRepo.getCommand("LIST").getName();
        String RETRCommandName = CommandsRepo.getCommand("RETR").getName();
        if (LISTCommandName.equals(newCommand.getName())
                && commandReply != null && commandReply.getStateCode() == LIST.READ_TO_REICEIVE_DATA) {
            dataEvent = new StringEvent("", DataDirection.RECEIVE, USER_PROCESS_THREAD_CONTROLLER_ID);
        }

//        logger.info("" + newCommand.getName() + " " + commandReply.getStateCode());
        if (RETRCommandName.equals(newCommand.getName())
                && commandReply != null && commandReply.getStateCode() == RETR.RETR_FILE_SUCCESS){
//            logger.info("it is a retr command ready to accept file");
            dataEvent = new FileEvent(
                    DEFAULT_USER_DIRECTORY
                            .resolve(newCommand.getParameters()[0])
                            .toString(), DataDirection.RECEIVE, USER_PROCESS_THREAD_CONTROLLER_ID);
        }

        return dataEvent;
    }



}
