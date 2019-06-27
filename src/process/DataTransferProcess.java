package process;

import reposity.file.FilesController;
import reposity.network.NetworkManager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * the interfaces of user Data Transfer Process for communication
 * @author Timcat cai
 * @version 2019/06/27
 */
public class DataTransferProcess implements DataTransferProcessable{
    private EventQueue eventQueue;
    private FilesController filesController;
    private NetworkManager networkManager;
    private ThreadPoolExecutor DTPThread;
    private ManagerProcessable protocolInterpreter;
    public DataTransferProcess(NetworkManager networkManager, ManagerProcessable protocolInterpreter){
        this.eventQueue = new EventQueue();
        filesController = new FilesController();
        this.networkManager  = networkManager;
        this.protocolInterpreter = protocolInterpreter;
        DTPThread = new ThreadPoolExecutor(7, 8, 5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        DTPThread.execute(new DataTransferProcessThread(
                filesController,  this.networkManager, protocolInterpreter, eventQueue)
        );
    }

    @Override
    public void sentData(Event event) {
        eventQueue.addEvent(event);
    }


    @Override
    public void receiveData(Event event) {
        eventQueue.addEvent(event);
    }

}
