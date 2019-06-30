package process.event;

import process.DataDirection;
import process.event.Event;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * communication info exchange unit of DTP and PI
 * @author Timcat cai
 * @version 2019/06/27
 */
public class FileEvent extends Event {


    public FileEvent(String data, DataDirection direction, String original) {
        super(data, direction, original);
    }

    public Path  getPath(){
        return Paths.get(super.getData());
    }


}
