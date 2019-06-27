package process;

import reposity.file.FilesController;
import reposity.network.NetworkManager;
import reposity.network.ServerNetworkManagerImp;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerDataTransferProcessImp extends DataTransferProcessable {

    private ThreadPoolExecutor serverDTPThread;

    public ServerDataTransferProcessImp() {
        super( new ServerNetworkManagerImp());
        serverDTPThread = new ThreadPoolExecutor(7, 8, 5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        serverDTPThread.execute(new ServerDataTransferProcessThread(
                super.filesController, super.networkManager, eventQueue)
        );
    }




}
