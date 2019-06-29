package server.Commands.definition;

import client.UserStateable;
import server.Commands.CommandExecuteResult;
import server.Commands.ReplyRepo;

public class PASS extends AbstractCommand {

    protected PASS(String name, String description, int parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public PASS(String name, String description, int parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        if(userState.getUserName() == null){
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
        }else if(verify(userState.getUserName(),parameters[0])){
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.LOGGED_IN_PROCEED));
            userState.setLoggedIn(true);
        }else{
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
        }

        return commandExecuteResult;
    }

    private boolean verify(String name,String password){
        return true;
    }
}
