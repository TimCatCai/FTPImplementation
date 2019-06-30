package reposity.network;

import utils.network.DataTransferWithConnectedSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * the user data transfer process = user_DPI
 *
 * @author TimcatCai
 * @version 2019/06/27
 */
public class ProviderNetworkManagerImp implements NetworkManager {

    private Logger logger = Logger.getLogger(ProviderNetworkManagerImp.class.getName());
    private ServerSocket userDataTransferringSocket;

    private final boolean isEstablshedOnce;
    private final static int BUFFER_SIZE = 10;
    /**
     * If data transfer port is not set by user, this is <code>DEFAULT_USER_DATA_TRANFER_PORT</code>
     */
    private int userDataPort;
    private Socket socketConnected;

    /**
     * 当该层作为循环监听来自客户端的连接的角色，通过该构造函数来设定端口，将<code>isEstablishOnce</code>设定为
     * false,表明可以重复建立连接。
     */

    public ProviderNetworkManagerImp(int userDataPort) {
        this.userDataPort = userDataPort;
        isEstablshedOnce = false;
        try {
            userDataTransferringSocket = new ServerSocket(this.userDataPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 当该层只是为客户端提供一次有效连接的时候，使用该构造函数，将<code>socketConnected</code>设定已连接的
     * <code>socketHasEstablished</code>, 同时置<code>isEstablishedOnce</code>为true.
     *
     * @param socketHasEstablished
     */
    public ProviderNetworkManagerImp(Socket socketHasEstablished) {
        socketConnected = socketHasEstablished;
        isEstablshedOnce = true;
    }

    public void openDataServerSocket() {
        try {
            socketConnected = userDataTransferringSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sentFile(InputStream in) {
        return false;
    }

    @Override
    public boolean receiveFile(OutputStream out) {
        boolean result = false;
        //server operation
        if (isConnected() && isEstablshedOnce) {
            try {
                InputStream inputStreamOfSocket = socketConnected.getInputStream();
                DataInputStream networkData = new DataInputStream(
                        new BufferedInputStream(inputStreamOfSocket)
                );

                DataOutputStream fileOutStream = new DataOutputStream(
                        new BufferedOutputStream(out)
                );
                /**
                 * @TODO check the size of file
                 */
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = networkData.read(buffer)) != -1 && networkData.available() != 0) {
                    fileOutStream.write(buffer);
                    fileOutStream.flush();
                    System.out.println("" + buffer + length);
                }

                System.out.println("end");
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //client data transfer process
        if(!isEstablshedOnce){
            if(!isConnected()){
                openDataServerSocket();
            }
        }
        return result;
    }

    @Override
    public boolean sentString(String data) {
        boolean result = false;
        if (isConnected()) {
            result = DataTransferWithConnectedSocket.sentString(socketConnected,data,logger);
        }
        return result;
    }

    @Override
    public String acceptString() {
        String result = null;


        //client data transfer process
        if(!isEstablshedOnce){
            if(!isConnected()){
                openDataServerSocket();
            }

            if(isConnected()){
                result = DataTransferWithConnectedSocket.acceptString(socketConnected,logger);
            }

        }
        //server
        else{
            if (isConnected()) {
                result = DataTransferWithConnectedSocket.acceptString(socketConnected,logger);
            }
        }


        return result;
    }

    private boolean isConnected() {
        return socketConnected != null && !socketConnected.isClosed();
    }


}
