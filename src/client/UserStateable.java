package client;

import reposity.file.AccessPrivilege;
import server.commands.definition.AbstractCommand;

import java.nio.file.Path;

public interface UserStateable {

    boolean isLoggedIn();
    void setLoggedIn(boolean loggedIn);
    void clearAllState();
    boolean isLoggedInForOtherCommands(AbstractCommand command);

    String getUserName();

    void setUserName(String userName);

    void setUserPrivilege(AccessPrivilege accessPrivilege);

    void setCurrentDirectory(Path currentDiretory);
    Path getCurrentDirectory();

}
