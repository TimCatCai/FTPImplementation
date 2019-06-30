package process.event;

import process.DataDirection;
import process.event.Event;

public class StringEvent extends Event {


    public StringEvent(String data, DataDirection direction, String original) {
        super(data, direction, original);
    }
}
