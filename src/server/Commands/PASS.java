package server.Commands;

import client.UserStateable;

public class PASS extends AbstractCommand {

    protected PASS(String name, String description, int parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        if(userState.getUserName() == null){
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
        }else if(verify(userState.getUserName(),parameters[0])){
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.LOGGED_IN_PROCEED));
        }else{
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_LOGIN));
        }

        return commandExecuteResult;
    }

    private boolean verify(String name,String password){
        return true;
    }
}
