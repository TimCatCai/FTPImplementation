package client;

import reposity.file.AccessPrivilege;
import server.commands.CommandsRepo;
import server.commands.definition.AbstractCommand;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UserState implements UserStateable{
    private String hostName;
    private int dataPort;
    private AccessPrivilege userPrivilege;
    private String userName;
    private boolean isLoggedIn = false;
    private Path currentDirectory;

    public UserState(String hostName, int dataPort, AccessPrivilege userPrivilege) {
        this.hostName = hostName;
        this.dataPort = dataPort;
        this.userPrivilege = userPrivilege;
        currentDirectory = Paths.get(System.getProperty("user.dir"));
    }

    public String getHostName() {
        return hostName;
    }

    public int getDataPort() {
        return dataPort;
    }

    public AccessPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public void clearAllState() {
        this.userPrivilege = null;
        currentDirectory = Paths.get(System.getProperty("user.dir"));
        isLoggedIn = false;
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public boolean isLoggedInForOtherCommands(AbstractCommand command) {
        String commandName =  command.getName().toUpperCase();
        boolean result = true;
        String userCommandName = CommandsRepo.getCommand("USER").getName();
        String passwordCommandName = CommandsRepo.getCommand("PASS").getName();
        String helpCommandName = CommandsRepo.getCommand("HELP").getName();
        if( !isLoggedIn
                && ! userCommandName.equals(commandName)
                && ! passwordCommandName.equals(commandName)
                && ! helpCommandName.equals(commandName)){
            result = false;
        }
        return result;
    }

    @Override
    public String  getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
       this.userName = userName;
    }

    @Override
    public void setUserPrivilege(AccessPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    @Override
    public void setCurrentDirectory(Path path) {
        this.currentDirectory = path;
    }

    @Override
    public Path getCurrentDirectory() {
        return currentDirectory;
    }
}
