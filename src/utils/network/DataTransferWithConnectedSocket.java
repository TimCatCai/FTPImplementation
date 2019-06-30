package utils.network;

import sun.rmi.runtime.Log;
import utils.data.transmission.ByteTransmission;
import utils.data.transmission.IntTransmission;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class DataTransferWithConnectedSocket {
    public static boolean sentString(Socket socket, String data, Logger logger){
        boolean result = false;
        try {
            OutputStream socketOutputStream = socket.getOutputStream();
            DataOutputStream stringOut = new DataOutputStream(socketOutputStream);

            byte[] bytesArrayOfSendingData = data.getBytes("UTF-8");
            int dataLength = bytesArrayOfSendingData.length;
            byte[] dataLengthBytes = IntTransmission.intToBytes(dataLength);

//            logger.info("the length of data sending is: " + dataLength);
            stringOut.write(dataLengthBytes);
            stringOut.flush();

//            logger.info("the content of data sending is: " + data);
            stringOut.write(bytesArrayOfSendingData);
            stringOut.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String acceptString(Socket socket,Logger logger){
        String result = null;
        try {
            InputStream in = socket.getInputStream();
            DataInputStream networkDataStream = new DataInputStream(in);

            byte[] lengthBytes = new byte[4];
            int lengthOfDataAccepting = 0;
            if (networkDataStream.read(lengthBytes) != -1) {
                lengthOfDataAccepting = ByteTransmission.bytesToInt(lengthBytes);
                //logger.info(" the length accepted: " + lengthOfDataAccepting);
            }

            byte[] dataAccepting = new byte[lengthOfDataAccepting];
            String dataAcceptingString = "";

            if (networkDataStream.read(dataAccepting) != -1) {
                dataAcceptingString = new String(dataAccepting, "UTF-8");
            }

            result = dataAcceptingString;
            //logger.info(" the data accepted: " + dataAcceptingString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
