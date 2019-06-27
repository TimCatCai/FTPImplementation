package process;

import reposity.network.UserNetworkManagerImp;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UserDataTransferProcessImp extends DataTransferProcessable {

    private final static int   DEFAULT_DATA_CONNECTING_PORT = 20;
    private ThreadPoolExecutor userDTPThread;
    public UserDataTransferProcessImp(){
        super(new UserNetworkManagerImp());
        userDTPThread = new ThreadPoolExecutor(7, 8, 5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());

    }

}
