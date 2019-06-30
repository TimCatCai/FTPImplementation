package client;

import process.event.Event;
import process.ManagerProcessable;

public interface UserManagerProccessable extends ManagerProcessable {
    void readCommandFromConsole(Event event);
}
