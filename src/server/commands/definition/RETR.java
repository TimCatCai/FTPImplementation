package server.commands.definition;

import client.UserStateable;
import process.DataDirection;
import process.event.FileEvent;
import server.commands.CommandExecuteResult;
import server.commands.Reply;
import server.commands.ReplyRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RETR extends AbstractCommand {
    protected RETR(String name, String description, int[] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public RETR(String name, String description, int[] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, int[] parameterNumber, UserStateable userState) {
        Path filePath = Paths.get(parameters[0]);
        FileEvent fileOperationEvent = null;
        Reply reply;
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        if(Files.exists(filePath)){
            fileOperationEvent = new FileEvent(filePath.toString() ,DataDirection.SENT,"RETR command");
            reply = ReplyRepo.getReply(ReplyRepo.FILE_READY);
        }else{

        }
        commandExecuteResult.setOperation(fileOperationEvent);


        return null;
    }
}
