package server;

import process.*;
import reposity.network.ConnectorNetworkManagerImp;

import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws InterruptedException {


        while (true) {

            ProtocolInterpreterable protocolInterpreterable = new UserProtocolInterper();
            new DataTransferProcess(new ConnectorNetworkManagerImp(), protocolInterpreterable)
                    .sentData(new FileEvent("G:\\temp\\server.txt", DataDirection.SENT));
            TimeUnit.SECONDS.sleep(500);
        }

    }

}
