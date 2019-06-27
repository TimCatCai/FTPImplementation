package server;

import process.*;
import reposity.network.ServerNetworkManagerImp;
import server.Commands.State;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws InterruptedException {


        while (true) {

            ProtocolInterpreterable protocolInterpreterable = new UserProtocolInterper();
            new DataTransferProcess(new ServerNetworkManagerImp(), protocolInterpreterable)
                    .sentData(new FileEvent("G:\\temp\\server.txt", DataDirection.SENT));
            TimeUnit.SECONDS.sleep(500);
        }

    }

}
