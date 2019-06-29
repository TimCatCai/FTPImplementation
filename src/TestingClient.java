import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TestingClient {
    public static void main(String [] args) throws IOException, InterruptedException {
        Logger logger = Logger.getLogger(TestingClient.class.getName());
        byte []bytes = new byte[4];
        bytes[0] = (byte) 0xff;
        bytes[1] = (byte) 0xff;
        bytes[2] = (byte) 0xff;
        bytes[3] = (byte) 0xff;
        int result = bytesToInt(bytes);
        System.out.println(result);
        Socket socket = new Socket("127.0.01",8080);
        OutputStream socketOutputStream =socket.getOutputStream();
        DataOutputStream stringOut = new DataOutputStream(socketOutputStream);

        String data = "hello world";
        byte [] byts = data.getBytes("UTF-8");
        logger.info("The length of sending data: " + data.length());

        stringOut.write(byts.length);
        stringOut.flush();
        TimeUnit.SECONDS.sleep(5);

        stringOut.write(byts);
        stringOut.flush();
        TimeUnit.SECONDS.sleep(10);
    }


     public static int bytesToInt(byte [] bytes){
        if(bytes ==null && bytes.length < 4){
            return 0;
        }
        int int0 = (bytes[0]&0x000000ff) ;
        int int1 = (bytes[1]&0x000000ff) << 8;
        int int2 = (bytes[2]&0x000000ff) << 16;
        int int3 = (bytes[3]&0x000000ff) << 24;

        return int0 | int1 | int2 | int3;
    }
}
