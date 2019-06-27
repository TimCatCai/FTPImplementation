package reposity.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * the server data transfer process = server_DPI
 *
 * @author TimcatCai
 * @version 2019/06/27
 */
public class ServerNetworkManagerImp implements NetworkManager {
    private final static int BUFFER_SIZE = 10;
    private final static int USER_DATA_TRANSFER_DEFAULT_PORT = 20;

    /**
     * the timeout of connecting is 30s
     */
    private final static int TIMEOUT_OF_CONNECTING = 30000;


    private int userDataTransferPort;
    private String userHostName;
    private Socket serverDataTransferSocket;

    public ServerNetworkManagerImp(String userHostName, int userDataTransferPort) {
        this.userHostName = userHostName;
        this.userDataTransferPort = userDataTransferPort;
    }

    public ServerNetworkManagerImp() {
        this.userHostName = "127.0.0.1";
        this.userDataTransferPort = USER_DATA_TRANSFER_DEFAULT_PORT;
    }


    /**
     * @param in the input stream of the file wanted to be sent
     * @return
     * @TODO call this method in a new thread
     */
    @Override
    public boolean sentFile(InputStream in) {
        boolean result = false;
        if (!isConnectionValid()) {
            connectToUserDataTransferProcess();
        }

        try {
            OutputStream socketOutputStream = serverDataTransferSocket.getOutputStream();
            DataOutputStream networkOutputStream = new DataOutputStream(
                    new BufferedOutputStream(socketOutputStream)
            );
            DataInputStream fileInputStream = new DataInputStream(
                    new BufferedInputStream(in)
            );
            byte[] buffer = new byte[BUFFER_SIZE];
            while (fileInputStream.read(buffer) != -1) {
                networkOutputStream.write(buffer);
                networkOutputStream.flush();
                System.out.println(buffer);
            }
            result = true;
            System.out.println("===========Sent!=============");
            TimeUnit.SECONDS.sleep(5);
            serverDataTransferSocket.close();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean receiveFile(OutputStream out) {
        return false;
    }

    @Override
    public boolean sentString(String data) {
        boolean result = false;
        boolean sendable = true;
        if (!isConnectionValid()) {
           if(!connectToUserDataTransferProcess()){
               sendable = false;
           }
        }

        if(sendable){
            try {
                OutputStream socketOutputStream = serverDataTransferSocket.getOutputStream();
                PrintWriter stringOut = new PrintWriter(
                        new OutputStreamWriter(
                                socketOutputStream,
                                StandardCharsets.UTF_8
                        ),
                        true
                );

                stringOut.print(data);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public String acceptString(String data) {
        return null;
    }


    private boolean connectToUserDataTransferProcess() {
        boolean result = false;
        if (!isConnectionValid()) {
            try {
                serverDataTransferSocket = new Socket(userHostName, userDataTransferPort);
                serverDataTransferSocket.setSoTimeout(TIMEOUT_OF_CONNECTING);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    serverDataTransferSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        return result;
    }

    private boolean isConnectionValid() {
        return serverDataTransferSocket != null && ! serverDataTransferSocket.isClosed();
    }
}
