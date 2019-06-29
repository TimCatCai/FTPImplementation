package utils.data.transmission;

public class IntTransmission {

    public static byte[] intToBytes(int data) {
        byte[] result = new byte[4];
        result[0] = (byte) (data & 0x000000ff);
        result[1] = (byte) (data >> 8 & 0x000000ff);
        result[2] = (byte) (data >> 16 & 0x000000ff);
        result[3] = (byte) (data >> 24 & 0x000000ff);
        return result;
    }
}
