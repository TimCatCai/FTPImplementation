package server;

import reposity.network.ServerNetworkManagerImp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String [] args){
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(8080);
//
//            Socket incoming = serverSocket.accept();
//            InputStream inputStream = incoming.getInputStream();
//            OutputStream outputStream = incoming.getOutputStream();
//
//            Scanner scanner = new Scanner(inputStream,"UTF-8");
//
//            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
//            out.println("Hello! Enter BYE to exit");
//
//            boolean done = false;
//            String line ;
//
//            while (!done && scanner.hasNextLine()){
//                line = scanner.nextLine();
//                System.out.println(line);
//                out.println("Echo: " + line);
//                if(line.trim().equals("BYE")){
//                    done = true;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ServerNetworkManagerImp server = new ServerNetworkManagerImp();
        try {
            FileInputStream in = new FileInputStream("G:\\temp\\server.txt");
            server.sentFile(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
