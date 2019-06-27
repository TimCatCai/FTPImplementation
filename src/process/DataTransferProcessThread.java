package process;

import reposity.file.FileAccess;
import reposity.network.NetworkManager;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * the implementation of <code>Runnable</code> to solve the data transfer
 * Transfer includes file and string
 *
 * @author Timcat Cai
 * @version 2019/06/27
 */
public class DataTransferProcessThread implements Runnable {
    private EventQueue dataEventsList;
    private FileAccess fileAccess;
    private NetworkManager DTPNetworkManager;
    private ManagerProcessable managerThreadable;

    public DataTransferProcessThread(FileAccess fileAccess, NetworkManager networkManager,
                                     ManagerProcessable protocolInterpreter,
                                     EventQueue dataEventsList) {
        this.fileAccess = fileAccess;
        this.DTPNetworkManager = networkManager;
        this.managerThreadable = protocolInterpreter;
        this.dataEventsList = dataEventsList;
    }

    @Override
    public void run() {
        while (true) {
            Event event = dataEventsList.takeEvent();
            if (event instanceof FileEvent) {
                if (event.getDirection() == DataDirection.SENT) {
                    InputStream in = fileAccess.readFile(((FileEvent) event).getPath());
                    //reject to read file
                    if(in == null){
                        managerThreadable.replyOperationResult(new Event("reject to read file",DataDirection.OWN));
                    }else{
                        DTPNetworkManager.sentFile(in);
                        event.setDirection(DataDirection.OWN);
                        managerThreadable.replyOperationResult(event);
                    }

                } else if (event.getDirection() == DataDirection.RECEIVE) {
                    OutputStream out = fileAccess.writeFile(((FileEvent) event).getPath());
                    //reject to write file
                    if(out == null){
                        managerThreadable.replyOperationResult(new Event("reject to write file",DataDirection.OWN));
                    }else{
                        DTPNetworkManager.receiveFile(out);
                        event.setDirection(DataDirection.OWN);
                        managerThreadable.replyOperationResult(event);
                    }

                }
            } else if (event instanceof StringEvent) {
                String result;
                if (event.getDirection() == DataDirection.SENT) {
                    DTPNetworkManager.sentString(event.getData());
                } else if (event.getDirection() == DataDirection.RECEIVE) {
                    result = DTPNetworkManager.acceptString();
                    event.setData(result);
                    managerThreadable.replyOperationResult(event);
                }
            }
        }
    }

}

