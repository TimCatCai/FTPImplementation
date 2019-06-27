package client;


import process.*;
import reposity.network.ProviderNetworkManagerImp;

import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        ProtocolInterpreterable pro = new ProtocolInterperImp();
        ProviderNetworkManagerImp userDTP = new ProviderNetworkManagerImp();
        userDTP.openDataServerSocket();
        DataTransferProcess dataTransferProcess = new DataTransferProcess(userDTP
        ,pro);

        dataTransferProcess.receiveData(new FileEvent("G:\\temp\\user.txt",DataDirection.RECEIVE));
        TimeUnit.SECONDS.sleep(500);
    }


}
