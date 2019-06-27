package reposity.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;

/**
 * all interfaces for operating accessing file operations
 * @author TimcatCai
 * @version 2019/06/26
 */
public interface FileAccess {

    /**
     * It will be chained  with  BufferedInputStream
     * @param filePath the path that the file  may exist
     * @return an input stream of the file if it exists and otherwise, null
     */
    FileInputStream readFile(Path filePath);

    /**
     * The privilege of writing is checked by protocol interpreter
     * It fails when writing is rejected by OS, or something wrong occurs
     * @param filePath the path that the file  may exist
     * @return null if writing is permitted, otherwise false
     */
    FileOutputStream writeFile(Path filePath);

}
