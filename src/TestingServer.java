import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
//        Logger logger = Logger.getLogger(TestingServer.class.getClass().getName());
//
//        ServerSocket serverSocket = new ServerSocket(8080);
//
//        Socket socket = serverSocket.accept();
//
//        InputStream in = socket.getInputStream();
//        DataInputStream networkDataStream = new DataInputStream(in);
//        byte[] bytes = new byte[4];
//        int length = 0;
//        if (networkDataStream.read(bytes) != -1) {
//            length = bytesToInt(bytes);
//        }
//
//        logger.info("The length is " + length);
//
//        byte [] data = new byte[length];
//
//        if(networkDataStream.read(data) != -1){
//
//        }
//
//        logger.info("The content are: " + new String(data,"UTF-8"));
//        TimeUnit.SECONDS.sleep(10);
        String a = "asdf|af";
        System.out.println(a.toUpperCase());
    }

    public static int bytesToInt(byte[] bytes) {
        if (bytes == null && bytes.length < 4) {
            return 0;
        }
        int int0 = (bytes[0] & 0x000000ff);
        int int1 = (bytes[1] & 0x000000ff) << 8;
        int int2 = (bytes[2] & 0x000000ff) << 16;
        int int3 = (bytes[3] & 0x000000ff) << 24;

        return int0 | int1 | int2 | int3;
    }

    public  static byte [] intToBytes(int data ){
        byte [] result = new byte[4];
        result[0] = (byte) (data &0x000000ff);
        result[1] = (byte) (data >> 8 &0x000000ff);
        result[2] = (byte) (data >> 16 &0x000000ff);
        result[3] = (byte) (data >> 24 &0x000000ff);
        return  result;
    }
}
