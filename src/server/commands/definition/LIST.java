package server.commands.definition;

import client.UserStateable;
import server.commands.CommandExecuteResult;
import server.commands.manager.CommandManager;

public class LIST extends AbstractCommand {
    protected LIST(String name, String description, int [] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public LIST(String name, String description, int [] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, int[] parameterNumber,UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        if(parameterNumber[0] == 0 ){

        }
        return commandExecuteResult;
    }
}
