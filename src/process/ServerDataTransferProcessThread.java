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
public class ServerDataTransferProcessThread implements Runnable {
    private EventQueue dataEventsList;
    private FileAccess fileAccess;
    private NetworkManager serverDTPNetworkManager;

    public ServerDataTransferProcessThread(FileAccess fileAccess, NetworkManager networkManager,
                                           EventQueue dataEventsList) {
        this.fileAccess = fileAccess;
        this.serverDTPNetworkManager = networkManager;
        this.dataEventsList = dataEventsList;
    }

    @Override
    public void run() {
        while (true){
            Event event = dataEventsList.takeEvent();
            if (event instanceof FileEvent) {
                if (event.getDirection() == DataDirection.SENT) {
                    InputStream in = fileAccess.readFile(((FileEvent) event).getPath());
                    serverDTPNetworkManager.sentFile(in);
                } else if (event.getDirection() == DataDirection.RECEIVE) {
                    OutputStream out = fileAccess.writeFile(((FileEvent) event).getPath());
                    serverDTPNetworkManager.receiveFile(out);
                }

            } else if (event instanceof StringEvent) {

            }
        }

    }
}
