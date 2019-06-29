package server.commands.manager;

import client.UserStateable;
import server.commands.*;
import server.commands.definition.AbstractCommand;

import java.util.HashMap;
import java.util.logging.Logger;

public class CommandManager implements CommandManagerable {
    private UserStateable userState;
    private Logger logger = Logger.getLogger(CommandManager.class.getName());
    public CommandManager(UserStateable userState) {
        this.userState = userState;
    }

    @Override
    public CommandExecuteResult parse(AbstractCommand command) {
        AbstractCommand realCommand = CommandsRepo.getCommand(command.getName());
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        boolean commandExist = true;
        boolean conditionPermitted = true;

        //no such command
        if (realCommand == null) {
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND));
            commandExist = false;
        }
        //command exits
        else {
            //check the number of command is suitable or not
            if (! isCommandParameterSuitable(command,realCommand)) {
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.PARAMETER_ERROR));
                conditionPermitted = false;
            }

            //check the user logs in or not.
            else if (!userState.isLoggedInForOtherCommands(command)) {
                commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
                conditionPermitted = false;
            }

        }


        // the command exists and the number of parameter is right, execute it
        if (commandExist && conditionPermitted) {
            commandExecuteResult = realCommand.execute(command.getParameters(),
                    command.getParameterNumber(), userState);
        }

        return commandExecuteResult;
    }

    private boolean isCommandParameterSuitable(AbstractCommand commandCheck,
                                               AbstractCommand commandInRepo){
        boolean result = false;
        int commandCheckParameterNumber = commandCheck.getParameterNumber()[0];
        for(int parameterNumber: commandInRepo.getParameterNumber()){
            if(parameterNumber == commandCheckParameterNumber){
                result = true;
            }
        }

        logger.info("the result of parameter check " + result);
        return result;
    }


}
