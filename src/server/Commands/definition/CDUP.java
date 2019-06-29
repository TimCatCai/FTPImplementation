package server.Commands.definition;

import client.UserStateable;
import server.Commands.CommandExecuteResult;

public class CDUP extends AbstractCommand {

    protected CDUP(String name, String description, int parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public CDUP(String name, String description, int parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, UserStateable userState) {

        return null;
    }
}
