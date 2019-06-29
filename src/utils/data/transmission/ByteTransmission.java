package utils.data.transmission;

public class ByteTransmission {

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
}
