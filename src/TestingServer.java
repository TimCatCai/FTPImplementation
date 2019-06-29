import java.io.IOException;

public class TestingServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        String a = "asdf|af";
        System.out.println(System.getProperty("user.dir"));
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
