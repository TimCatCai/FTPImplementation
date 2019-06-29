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
        //new command, that is it isn't in the command cache
        if(realCommand == null){
           realCommand =   CommandsRepo.getCommand(command.getName());
            //no such command
            if(realCommand == null){
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND));
                commandExist = false;
            }
            //the command exists and add it to cache
            else{
                cachedCommands.put(realCommand.getName(),realCommand);
            }
        }


        // the command exists, execute it
        if(commandExist){
            commandExecuteResult = realCommand.execute(command.getParameters(),userState);
            CommandState nextCommandState = commandExecuteResult.getNextCommandState();
            if(nextCommandState != null){
                cachedCommands.get(realCommand.getName()).setCurrentSate(nextCommandState);
            }
        }

        return commandExecuteResult;
    }
}
