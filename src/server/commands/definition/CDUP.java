package server.commands.definition;

import client.UserStateable;
import server.commands.CommandExecuteResult;
import server.commands.Reply;

import java.nio.file.Path;

public class CDUP extends AbstractCommand {

    public CDUP(String name, String description, int[] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public CDUP(String name, String description, int[] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, int [] parameterNumber,UserStateable userState) {
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        Path currentDirectory = userState.getCurrentDirectory();
        Path parentPath =  currentDirectory.getParent();
        String replyString;
        int replyCode;
        if(parentPath == null){
            replyString = " Requested action not taken\n"
                    + "No parent directory";
            replyCode = 553;
        }else{
            userState.setCurrentDirectory(parentPath);
            replyString = " change to parent directory\n" + "current directory: " + parentPath;
            replyCode = 200;
        }
        Reply reply = new Reply(replyCode,replyString);
        commandExecuteResult.setReplyForCommand(reply);

        return commandExecuteResult;
    }
}
