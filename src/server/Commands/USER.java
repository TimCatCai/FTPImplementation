package server.Commands;

import client.UserState;
import client.UserStateable;
import reposity.file.AccessPrivilege;

import java.util.logging.Logger;

public class USER extends AbstractCommand {
    private final String FIRST_STATE = "first time";
    private final int FIRST_STATE_VALUE = 0;
    private final String ALREADY_REGISTER_STATE = "register";
    private final int ALREADY_REGISTER_STATE_VALUE = 1;

    private Logger logger= Logger.getLogger(USER.class.getName());
    public USER(String name, String description, int parameterNumber, String [] parameters) {
        super(name, description,parameterNumber,parameters);

        CommandState temp;
        firstCommandState = new CommandState(FIRST_STATE,FIRST_STATE_VALUE);
        temp  = new CommandState(ALREADY_REGISTER_STATE,ALREADY_REGISTER_STATE_VALUE);
        firstCommandState.setNextCommandState(temp);
        currentSate = firstCommandState;
    }

    @Override
    public CommandExecuteResult execute(String [] parameters, UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        this.parameters = parameters;
        int stateCode;
        if(parameters == null ||parameters.length != parameterNumber){
            stateCode =  ReplyRepo.PARAMETER_ERROR;
        }else {
            String userName = parameters[0];
            if(isFirstVisit(userName)){
                currentSate = firstCommandState;
            }else {
                currentSate = firstCommandState.getNextCommandState();
                commandExecuteResult.setNextCommandState(currentSate);
            }
            stateCode = ReplyRepo.LOGGED_IN_PROCEED;
            userState.setUserPrivilege(AccessPrivilege.ORDINARY);
        }

        logger.info("StateCode: " + stateCode );
        logger.info("Reply content: " + ReplyRepo.getReply(stateCode).toString());

        commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(stateCode));
        return commandExecuteResult;
    }

    private boolean isFirstVisit(String userName){
        return true;
    }
}
