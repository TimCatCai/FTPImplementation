package process.event;

import process.DataDirection;
import process.event.Event;

public class ReplyEvent extends Event {

    public ReplyEvent(String data, DataDirection direction, String original) {
        super(data, direction, original);
    }
}
