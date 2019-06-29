package reposity.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * implementation of PathAccess
 * @author TimcatCai
 * @version 2019/06/26
 */
public class PathController implements PathAccess {


    @Override
    public boolean deletePath(Path path) {
        boolean result = false ;
        try {
            if(isDirectoryExists(path) && isDirectory(path)){
                if(isDirectoryEmpty(path)){
                    result= Files.deleteIfExists(path);
                }else {
                    System.out.println("the dir is not empty");
                }
            }
            else {
                System.out.println("dir doesn't not exits or no a dir");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isDirectoryExists(Path path) {
        return Files.exists(path);
    }

    @Override
    public boolean isDirectoryEmpty(Path path) {
        Stream<Path> paths;
        boolean result = false ;
        try {
            paths = Files.list(path);
            if(paths.count() <= 0 ){
               result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }
}
