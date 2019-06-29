package client;

import process.*;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;

import java.util.Scanner;
import java.util.logging.Logger;


public class Controller implements UserManagerProccessable{
    private EventQueue clientEventQueue;
    private String hostName;
    private int port;
    private DataTransferProcessable userPI;
    private DataTransferProcessable userDTP;
    private ViewInterface view;

    private final int DEFAULT_SERVER_PORT = 21;
    private final int DEFAULT_USER_DATA_PORT = 20;


    private final String USER_PI = "user_pi";
    private final  String USER_DTP = "user_dtp";

    private final String USER_PROCCESS_THREAD_CONTROLLER = "user_process_thread_controller";
    private Logger logger = Logger.getLogger(Controller.class.getName());

    public Controller(ViewInterface view){
        this.view = view;
        clientEventQueue = new EventQueue();
    }

    public void start(){
//        hostName = view.read("Hostname");
//        port = Integer.parseInt(view.read("port"));
        hostName = "127.0.0.1";
        userPI = new DataTransferProcess(new ConnectorNetworkManagerImp(hostName,DEFAULT_SERVER_PORT),
                this,
                USER_PI
                );
        userDTP = new DataTransferProcess(new ProviderNetworkManagerImp(DEFAULT_USER_DATA_PORT),
                this,
                USER_DTP
                );
        Event newEventFromClientEventQueue;
        String data;
        Scanner scanner = new Scanner(System.in);
        StringBuilder commandWillSent;
        while(true){
            data = scanner.nextLine();

            String [] commandInput  = data.split(" ");
            commandWillSent= new StringBuilder();
            for(int i = 0; i <  commandInput.length - 1; i++){
                commandWillSent.append(commandInput[i]);
                //add delimiter for each one
                commandWillSent.append("|");
            }
            //The last one has no delimiter
            commandWillSent.append(commandInput[commandInput.length - 1]);
            logger.info(commandWillSent.toString());

            userPI.sentData(new StringEvent(commandWillSent.toString(),DataDirection.SENT,
                    USER_PROCCESS_THREAD_CONTROLLER));
            userPI.receiveData(new StringEvent("",DataDirection.RECEIVE,
                    USER_PROCCESS_THREAD_CONTROLLER));
            newEventFromClientEventQueue = clientEventQueue.takeEvent();
            view.display(newEventFromClientEventQueue.getData());
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
}
