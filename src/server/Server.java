package server;

import process.*;
import reposity.network.ConnectorNetworkManagerImp;

import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws InterruptedException {

        while (true) {

            ServerProcessable serverProcessable = new ProtocolInterperImp();
            new DataTransferProcess(new ConnectorNetworkManagerImp(), serverProcessable)
                    .sentData(new FileEvent("G:\\temp\\server.txt", DataDirection.SENT));
            TimeUnit.SECONDS.sleep(500);
        }

    }

}
