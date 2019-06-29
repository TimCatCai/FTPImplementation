package server;

import process.*;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws InterruptedException, IOException {
        ThreadPoolExecutor proccessThread  = new ThreadPoolExecutor(7, 8, 5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        ServerSocket serverSocket = new ServerSocket(21);
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            proccessThread.execute(new ServerProcessThread(socket));
        }
//        ProviderNetworkManagerImp networkManagerImp = new ProviderNetworkManagerImp(21);
//        networkManagerImp.openDataServerSocket();
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(networkManagerImp.acceptString());
    }

}
