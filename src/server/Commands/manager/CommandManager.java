package server.Commands.manager;

import client.UserStateable;
import server.Commands.*;
import server.Commands.definition.AbstractCommand;

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
        boolean conditionPermitted = true;
        //new command, that is it isn't in the command cache
        if(realCommand == null){
           realCommand =   CommandsRepo.getCommand(command.getName());
            //no such command
            if(realCommand == null){
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND));
                commandExist = false;
            } //the command exists and add it to cache and parameter right;

            //command exits
            else{
                //check the number of command is suitable or not
                if(realCommand.getParameterNumber() !=  command.getParameterNumber()) {
                    commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.PARAMETER_ERROR));
                    conditionPermitted = false;
                }

                //check the user logs in or not.
                else if( !userState.isLoggedInForOtherCommands(command)){
                    commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
                    conditionPermitted = false;
                }
                // exits and suits previous condition
                else{
                    cachedCommands.put(realCommand.getName(),realCommand);
                }
            }


        }


        // the command exists and the number of parameter is right, execute it
        if(commandExist && conditionPermitted){
            commandExecuteResult = realCommand.execute(command.getParameters(),userState);
            CommandState nextCommandState = commandExecuteResult.getNextCommandState();
            if(nextCommandState != null){
                cachedCommands.get(realCommand.getName()).setCurrentSate(nextCommandState);
            }
        }

        return commandExecuteResult;
    }
}
