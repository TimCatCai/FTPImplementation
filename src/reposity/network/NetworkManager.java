package reposity.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * managers the network operations of  data transfer process
 * @author TimCat Cai
 * @version 2019/06/27
 */
public interface NetworkManager {
    /**
     * the <code>in</code> stream is  a FileInputStream built by the file path
     * @param in the input stream of the file wanted to be sent
     * @return true if successfully, false else
     */
    boolean sentFile(InputStream in);

    /**
     * the <code>out</code> stream is a FileOutputStream built by the file path
     * @param out he output stream of the file where data will  be stored
     * @return true if successfully, false else
     */
    boolean receiveFile(OutputStream out);

    boolean sentString(String data);

    String acceptString(String data);
}
