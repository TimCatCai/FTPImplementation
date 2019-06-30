package reposity.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

/**
 * Manages all files operations
 * It can be only used in DIP thread
 *
 * @author TimcatCai
 * @version 2019/06/26
 */
public class FilesController implements FileAccess {

    public FilesController() {
    }

    @Override
    public FileInputStream readFile(Path filePath) {
        File file = filePath.toFile();
        FileInputStream fileInputStream = null;
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fileInputStream;
    }

    @Override
    public FileOutputStream writeFile(Path filePath) {
        FileOutputStream out = null;
        File file = filePath.toFile();

        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;
    }


}
