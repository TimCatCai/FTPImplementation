package server.Commands;

import java.util.logging.Logger;

public class USER extends AbstractCommand {
    private final String FIRST_STATE = "first time";
    private final int FIRST_STATE_VALUE = 0;
    private final String ALREADY_REGISTER_STATE = "register";
    private final int ALREADY_REGISTER_STATE_VALUE = 1;

    private Logger logger= Logger.getLogger(USER.class.getName());
    public USER(String name, String description, int parameterNumber, String [] parameters) {
        super(name, description,parameterNumber,parameters);

        State temp;
        firstState = new State(FIRST_STATE,FIRST_STATE_VALUE);
        temp  = new State(ALREADY_REGISTER_STATE,ALREADY_REGISTER_STATE_VALUE);
        firstState.setNextState(temp);
        currentSate = firstState;
    }

    @Override
    public CommandExecuteResult execute(String [] parameters) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        this.parameters = parameters;
        int stateCode;
        if(parameters == null ||parameters.length != parameterNumber){
            stateCode =  ReplyRepo.PARAMETER_ERROR;
        }else {
            String userName = parameters[0];
            if(isFirstVisit(userName)){
                currentSate = firstState;
            }else {
                currentSate = firstState.getNextState();
                commandExecuteResult.setNextState(currentSate);
            }
            stateCode = ReplyRepo.LOGGED_IN_PROCEED;
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
