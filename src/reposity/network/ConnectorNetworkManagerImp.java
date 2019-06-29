package reposity.network;

import utils.network.DataTransferWithConnectedSocket;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * the server data transfer process = server_DPI
 *
 * @author TimcatCai
 * @version 2019/06/27
 */
public class ConnectorNetworkManagerImp implements NetworkManager {

    private Logger logger = Logger.getLogger(ConnectorNetworkManagerImp.class.getName());
    private final static int BUFFER_SIZE = 10;
    /**
     * the timeout of connecting is 30s
     */
    private final static int TIMEOUT_OF_CONNECTING = 30000;


    private int userDataTransferPort;
    private String userHostName;
    private Socket providerDataTransferSocket;

    public ConnectorNetworkManagerImp(String userHostName, int userDataTransferPort) {
        this.userHostName = userHostName;
        this.userDataTransferPort = userDataTransferPort;
    }

    /**
     * @param in the input stream of the file wanted to be sent
     * @return
     * @TODO call this method in a new thread
     */
    @Override
    public boolean sentFile(InputStream in) {
        boolean result = false;
        boolean connectable = true;
        if (!isConnectionValid()) {
            connectable = connectToUserDataTransferProcess();
        }
        if (connectable) {
            try {
                OutputStream socketOutputStream = providerDataTransferSocket.getOutputStream();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            sendable = connectToUserDataTransferProcess();
        }
        if (sendable) {
            DataTransferWithConnectedSocket.sentString(providerDataTransferSocket,data,logger);
        }
        return result;
    }

    @Override
    public String acceptString() {
        String result = null;

        if (isConnectionValid()) {
           result =  DataTransferWithConnectedSocket.acceptString(providerDataTransferSocket,logger);
        }
        return result;
    }


    private boolean connectToUserDataTransferProcess() {
        boolean result = false;
        if (!isConnectionValid()) {
            try {
                providerDataTransferSocket = new Socket(userHostName, userDataTransferPort);
                providerDataTransferSocket.setSoTimeout(TIMEOUT_OF_CONNECTING);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    providerDataTransferSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        return result;
    }

     private boolean isConnectionValid() {
        return providerDataTransferSocket != null && !providerDataTransferSocket.isClosed();
    }
}
