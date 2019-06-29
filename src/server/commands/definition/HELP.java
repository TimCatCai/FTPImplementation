package server.commands.definition;

import client.UserStateable;
import server.commands.CommandExecuteResult;
import server.commands.CommandsRepo;
import server.commands.Reply;

public class HELP extends AbstractCommand {
    protected HELP(String name, String description, int parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public HELP(String name, String description, int parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, UserStateable userState) {
        String commandsHelp = CommandsRepo.seeAllCommands();
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        commandExecuteResult.setReplyForCommand(new Reply(211,commandsHelp));
        return  commandExecuteResult;
    }
}
