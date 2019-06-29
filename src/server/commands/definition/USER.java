package server.commands.definition;

import client.UserStateable;
import reposity.file.AccessPrivilege;
import server.commands.CommandExecuteResult;
import server.commands.ReplyRepo;

import java.util.logging.Logger;

public class USER extends AbstractCommand {
    private Logger logger = Logger.getLogger(USER.class.getName());

    public USER(String name, String description, int[] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public USER(String name, String description, int[] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters,int[] parameterNumber, UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        this.parameters = parameters;
        int stateCode;


        String userName = parameters[0];
        //if user has logged in before clear all the user state
        if (userState.isLoggedIn()) {
            userState.clearAllState();
        }
        stateCode = ReplyRepo.NEED_PASSWORD;
        userState.setUserPrivilege(AccessPrivilege.ORDINARY);
        userState.setUserName(userName);


        logger.info("StateCode: " + stateCode);
        logger.info("Reply content: " + ReplyRepo.getReply(stateCode).toString());

        commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(stateCode));
        return commandExecuteResult;
    }

    private boolean isFirstVisit(String userName) {
        return true;
    }
}
