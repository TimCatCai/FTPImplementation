package client;

import process.Event;
import process.ManagerProcessable;

public interface UserManagerProccessable extends ManagerProcessable {
    void readCommandFromConsole(Event event);
}
