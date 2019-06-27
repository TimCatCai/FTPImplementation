package reposity.file;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gets the status of the file that user wants to operate
 * @author TimcatCai
 * @version 2019/06/26
 */
public class FileStatus {
    private AccessPrivilege userPrivilege;
    public FileStatus(AccessPrivilege userPrivilege){
        this.userPrivilege = userPrivilege;
    }

    public boolean isReadable(Path filePath){
        return Files.isReadable(filePath);
    }

    public boolean isWriteable(Path filePath){
        if(userPrivilege == AccessPrivilege.ADMIN && Files.isWritable(filePath)){
            return true;
        }else{
            return false;
        }
    }

    public void setUserPrivilege(AccessPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }
}
