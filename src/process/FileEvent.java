package process;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * communication info exchange unit of DTP and PI
 * @author Timcat cai
 * @version 2019/06/27
 */
public class FileEvent extends Event {

    public FileEvent(String data,DataDirection direction) {
        super(data,direction);

    }

    public Path  getPath(){
        return Paths.get(super.getData());
    }


}
