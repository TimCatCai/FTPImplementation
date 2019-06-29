package process;

import reposity.file.FileAccess;
import reposity.network.NetworkManager;
import server.Commands.Reply;
import sun.rmi.runtime.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * the implementation of <code>Runnable</code> to solve the data transfer
 * Transfer includes file and string
 *
 * @author Timcat Cai
 * @version 2019/06/27
 */
public class DataTransferProcessThread implements Runnable {
    private Logger logger;
    private EventQueue dataEventsList;
    private FileAccess fileAccess;
    private NetworkManager DTPNetworkManager;
    private ManagerProcessable managerThreadable;

    private String threadName;

    public DataTransferProcessThread(FileAccess fileAccess, NetworkManager networkManager,
                                     ManagerProcessable protocolInterpreter,
                                     EventQueue dataEventsList,String threadName) {
        this.fileAccess = fileAccess;
        this.DTPNetworkManager = networkManager;
        this.managerThreadable = protocolInterpreter;
        this.dataEventsList = dataEventsList;
        this.threadName = threadName;
        logger = Logger.getLogger(threadName);
    }

    @Override
    public void run() {
        while (true) {
            Event event = dataEventsList.takeEvent();
            ReplyEvent toManagerReplyEvent = null;
            StringEvent acceptDataToManager;
            if (event instanceof FileEvent) {
                if (event.getDirection() == DataDirection.SENT) {
                    InputStream in = fileAccess.readFile(((FileEvent) event).getPath());
                    //reject to read file
                    if(in == null){
                        toManagerReplyEvent = new ReplyEvent("reject to read file",event.getDirection(),threadName);
                    }else{
                        DTPNetworkManager.sentFile(in);
                        toManagerReplyEvent = new ReplyEvent(event.getData(),event.getDirection(),threadName);
                    }

                } else if (event.getDirection() == DataDirection.RECEIVE) {
                    OutputStream out = fileAccess.writeFile(((FileEvent) event).getPath());
                    //reject to write file
                    if(out == null){
                        toManagerReplyEvent = new ReplyEvent("reject to write file",event.getDirection(),threadName);

                    }else{
                        //the assumption sending  is always finished  successfully
                        DTPNetworkManager.receiveFile(out);
                        toManagerReplyEvent = new ReplyEvent(event.getData(),event.getDirection(),threadName);
                    }

                }

                managerThreadable.replyOperationResult(toManagerReplyEvent);
            } else if (event instanceof StringEvent) {
                String result;
                if (event.getDirection() == DataDirection.SENT) {
                    DTPNetworkManager.sentString(event.getData());
                } else if (event.getDirection() == DataDirection.RECEIVE) {

                    result = DTPNetworkManager.acceptString();
                    acceptDataToManager = new StringEvent(result,DataDirection.RECEIVE,threadName);
                    logger.info( " the result get from Network: " + event.getOriginal() + ": " + result);
                    managerThreadable.replyOperationResult(acceptDataToManager);
                }
            }
        }
    }

}

