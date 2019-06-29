package server.Commands.definition;

import client.UserStateable;
import reposity.file.AccessPrivilege;
import server.Commands.CommandExecuteResult;
import server.Commands.CommandState;
import server.Commands.ReplyRepo;

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

    public USER(String name, String description, int parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String [] parameters, UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        this.parameters = parameters;
        int stateCode;
        if(parameters == null ||parameters.length != parameterNumber){
            stateCode =  ReplyRepo.PARAMETER_ERROR;
        }
        else {
            String userName = parameters[0];
            if(isFirstVisit(userName)){
                currentSate = firstCommandState;
            }else {
                currentSate = firstCommandState.getNextCommandState();
                commandExecuteResult.setNextCommandState(currentSate);
            }
            //if user has logged in before clear all the user state
            if(userState.isLoggedIn()){
                userState.clearAllState();
            }
            stateCode = ReplyRepo.NEED_PASSWORD;
            userState.setUserPrivilege(AccessPrivilege.ORDINARY);
            userState.setUserName(userName);
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
