package server.commands.definition;

import client.UserStateable;
import reposity.path.PathAccess;
import reposity.path.PathController;
import server.commands.CommandExecuteResult;
import server.commands.Reply;
import server.commands.ReplyRepo;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CWD  extends AbstractCommand {


    protected CWD(String name, String description, int[] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public CWD(String name, String description, int[] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters,int[] parameterNumber, UserStateable userState) {
        PathAccess pathAccess = new PathController();
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        Path newPath = Paths.get(parameters[0]);

        if(pathAccess.isDirectoryExists(newPath)){
            userState.setCurrentDirectory(newPath);
            Reply replyForSuccess = new Reply(200,"directory changed to " + newPath.toString());
            commandExecuteResult.setReplyForCommand(replyForSuccess);
        }else{
            commandExecuteResult.setReplyForCommand(ReplyRepo.getReply(ReplyRepo.NO_SUCH_DIRECTORY));
        }

        return commandExecuteResult;
    }
}
