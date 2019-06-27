package reposity.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * the user data transfer process = user_DPI
 * @author TimcatCai
 * @version 2019/06/27
 */
public class ProviderNetworkManagerImp implements NetworkManager {
    private ServerSocket userDataTransferringSocket;
    private final  static int  BUFFER_SIZE = 10;
    /**
     *  If data transfer port is not set by user, this is <code>DEFAULT_USER_DATA_TRANFER_PORT</code>
     */
    private int userDataPort;
    private final  static int DEFAULT_USER_DATA_TRANSFER_PORT = 20;
    private Socket socketConnected;
    public ProviderNetworkManagerImp(int userDataPort) {
        this.userDataPort = userDataPort;
    }

    public ProviderNetworkManagerImp(){
        userDataPort = DEFAULT_USER_DATA_TRANSFER_PORT;

    }

    public void openDataServerSocket( ){
        /**
         * @TODO remember to start a new thread for the ServerSocket
         * @TODO remember to solve the problem how to sent the dataOutputStream to the thread
         */
        try {
            userDataTransferringSocket = new ServerSocket(this.userDataPort);
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
       if(isConnected()){
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
               byte [] buffer = new  byte[BUFFER_SIZE];
               int length;
               while((length = networkData.read(buffer)) != -1 && networkData.available() != 0){
                   fileOutStream.write(buffer);
                   fileOutStream.flush();
                   System.out.println(""+buffer+length);
               }

               System.out.println("end");
               result = true;
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
        return result;
    }

    @Override
    public boolean sentString(String data) {
        return false;
    }

    @Override
    public String acceptString() {
        String result = null;
        if(isConnected()){
            try {
                InputStream in = socketConnected.getInputStream();
                Scanner networkScanner = new Scanner(in,"UTF-8");
                StringBuilder builder = new StringBuilder();
                while (networkScanner.hasNextLine()){
                    builder.append(networkScanner.nextLine());
                }
                result = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean isConnected(){
        return socketConnected != null && !socketConnected.isClosed();
    }


}
