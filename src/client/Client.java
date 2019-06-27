package client;


import process.*;
import reposity.network.UserNetworkManagerImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        ProtocolInterpreterable pro = new UserProtocolInterper();
        UserNetworkManagerImp userDTP = new UserNetworkManagerImp();
        userDTP.openDataServerSocket();
        DataTransferProcess dataTransferProcess = new DataTransferProcess(userDTP
        ,pro);

        dataTransferProcess.receiveData(new FileEvent("G:\\temp\\user.txt",DataDirection.RECEIVE));
        TimeUnit.SECONDS.sleep(500);
    }


}
