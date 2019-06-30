package reposity.path;

import java.nio.file.Path;

/**
 * all interfaces for protocol interpreter to operate directory operations
 * @author TimcatCai
 * @version 2019/06/26
 */
public interface PathAccess {

    /**
     * deletes specified directory
     * If the directory is not empty, it returns false and prints error.
     * If the directory is
     * @param path the path of directory that will be deleted
     * @return true if delete the path successfully, otherwise false
     */
    boolean deletePath(Path path);

    /**
     * encapsulates the <code>Files.exists()</code> interface
     * @param path the path of the dir
     * @return true if exists, otherwise false
     */
    boolean isDirectoryExists(Path path);

    /**
     * checks the dir is empty or not before deleting
     * @param path the path of the dir
     * @return true if the dir is empty, false otherwise
     */
    boolean isDirectoryEmpty(Path path);

    /**
     * encapsulates the <code>Files.isDirectory()</code> interface
     * @param path the path of the dir
     * @return true if it is a dir, false otherwise
     */
    boolean isDirectory(Path path);


    String listDirectory(Path path);

}
