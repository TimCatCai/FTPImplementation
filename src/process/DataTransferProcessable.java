package process;

import reposity.file.FilesController;
import reposity.network.NetworkManager;

/**
 * the interfaces of user Data Transfer Process for communication
 * @author Timcat cai
 * @version 2019/06/27
 */
public class DataTransferProcessable {
    protected EventQueue eventQueue;
    protected FilesController filesController;
    protected NetworkManager networkManager;
    protected DataTransferProcessable(NetworkManager networkManager){
        this.eventQueue = new EventQueue();
        filesController = new FilesController();
        this.networkManager  = networkManager;
    }
    /**
     * sets data through DTP
     * @param event communication info for the DTP
     */

    public void sentData(Event event) {
        eventQueue.addEvent(event);
    }



    /**
     * sets data frome DTP
     * @param event communication info for the DTP
     */
    public void receiveData(Event event) {
        eventQueue.addEvent(event);
    }

}
