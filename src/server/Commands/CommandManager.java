package server.Commands;

import client.UserState;
import client.UserStateable;

import java.util.HashMap;

public class CommandManager implements CommandManagerable {
    private HashMap<String,AbstractCommand> cachedCommands
            = new HashMap<>();
    private UserStateable userState;
    public CommandManager(UserStateable userState){
        this.userState = userState;
    }

    @Override
    public CommandExecuteResult parse(AbstractCommand command) {
        AbstractCommand realCommand = cachedCommands.get(command.getName());
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        boolean commandExist = true;
        boolean parameterNameNumberRight = true;
        //new command, that is it isn't in the command cache
        if(realCommand == null){
           realCommand =   CommandsRepo.getCommand(command.getName());
            //no such command
            if(realCommand == null){
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND));
                commandExist = false;
            }

            else if(realCommand.getParameterNumber() !=  command.getParameterNumber()) {
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.PARAMETER_ERROR));
                parameterNameNumberRight = false;
            }
            //the command exists and add it to cache and parameter right;
            else{
                cachedCommands.put(realCommand.getName(),realCommand);
            }
        }


        // the command exists and the number of parameter is right, execute it
        if(commandExist && parameterNameNumberRight){
            commandExecuteResult = realCommand.execute(command.getParameters(),userState);
            CommandState nextCommandState = commandExecuteResult.getNextCommandState();
            if(nextCommandState != null){
                cachedCommands.get(realCommand.getName()).setCurrentSate(nextCommandState);
            }
        }

        return commandExecuteResult;
    }
}
