package client;

import reposity.file.AccessPrivilege;

public class UserState implements UserStateable{
    private String hostName;
    private int dataPort;
    private AccessPrivilege userPrivilege;

    public UserState(String hostName, int dataPort, AccessPrivilege userPrivilege) {
        this.hostName = hostName;
        this.dataPort = dataPort;
        this.userPrivilege = userPrivilege;
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
    public void setUserPrivilege(AccessPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }
}
