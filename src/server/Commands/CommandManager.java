package server.Commands;

import java.util.HashMap;

public class CommandManager implements CommandManagerable {
    private HashMap<String,AbstractCommand> cachedCommands
            = new HashMap<>();

    public CommandManager(){
    }
    @Override
    public Reply parse(AbstractCommand command) {

        AbstractCommand realCommand = cachedCommands.get(command.getName());

        //new command, that is it isn't in the command cache
        if(realCommand == null){
           realCommand =   CommandsRepo.getCommand(command.getName());
        }

        //no such command
        if(realCommand == null){
            return ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND);
        }
        // the command exists, execute it and add to cache
        else{
             return realCommand.execute(command.getParameters());
        }
    }
}
