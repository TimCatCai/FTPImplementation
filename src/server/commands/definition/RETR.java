package server.commands.definition;

import client.UserStateable;
import process.DataDirection;
import process.event.FileEvent;
import reposity.path.PathAccess;
import reposity.path.PathController;
import server.commands.CommandExecuteResult;
import server.commands.Reply;
import server.commands.ReplyRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RETR extends AbstractCommand {
    public static int RETR_FILE_SUCCESS = 250;
    protected RETR(String name, String description, int[] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public RETR(String name, String description, int[] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, int[] parameterNumber, UserStateable userState) {
        Path filePath = userState.getCurrentDirectory().resolve(Paths.get(parameters[0]));

        FileEvent fileOperationEvent = null;
        Reply replyForFileOperation;
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        if(filePath != null && Files.exists(filePath)){
            fileOperationEvent = new FileEvent(filePath.toString() ,DataDirection.SENT,"RETR command");
            replyForFileOperation = ReplyRepo.getReply(ReplyRepo.FILE_READY);
        }else{
            replyForFileOperation = ReplyRepo.getReply(ReplyRepo.FILE_OPERATION_FAIL);
        }

        commandExecuteResult.setOperation(fileOperationEvent);
        commandExecuteResult.setReplyForCommand(replyForFileOperation);

        return commandExecuteResult;
    }
}
