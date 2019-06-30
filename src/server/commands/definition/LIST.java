package server.commands.definition;

import client.UserStateable;
import process.DataDirection;
import process.event.Event;
import process.event.StringEvent;
import reposity.path.PathAccess;
import reposity.path.PathController;
import server.commands.CommandExecuteResult;
import server.commands.Reply;
import server.commands.ReplyRepo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class LIST extends AbstractCommand {

    public static int READ_TO_REICEIVE_DATA = 200;
    private static Logger logger = Logger.getLogger(LIST.class.getName());
    protected LIST(String name, String description, int [] parameterNumber, String[] parameters) {
        super(name, description, parameterNumber, parameters);
    }

    public LIST(String name, String description, int [] parameterNumber) {
        super(name, description, parameterNumber);
    }

    @Override
    public CommandExecuteResult execute(String[] parameters, int[] parameterNumber,UserStateable userState) {
        PathAccess pathAccess = new PathController();
        Path targetListingPath;
        Event operation = null;
        Reply replyForCommand;
        CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
        String entriesString = "";
        logger.info(""+ parameterNumber[0]);
        if(parameterNumber[0] == 0 ){
            targetListingPath = userState.getCurrentDirectory();
            entriesString = pathAccess.listDirectory(targetListingPath);
            operation = new StringEvent(entriesString,DataDirection.SENT,"List command");
            replyForCommand = new Reply(200,"list current directory");

        }
        else if (parameterNumber[0] == 1 && parameters != null){
            targetListingPath = Paths.get(parameters[0]);
            if(pathAccess.isDirectoryExists(targetListingPath)){
                entriesString = pathAccess.listDirectory(targetListingPath);
                operation = new StringEvent(entriesString,DataDirection.SENT,"List command");
                replyForCommand = new Reply(200,"list specify directory");
            }else{
                replyForCommand = ReplyRepo.getReply(ReplyRepo.NO_SUCH_DIRECTORY);
            }
        }else{
            replyForCommand = ReplyRepo.getReply(ReplyRepo.NO_SUCH_DIRECTORY);
        }

        commandExecuteResult.setReplyForCommand(replyForCommand);
        commandExecuteResult.setOperation(operation);
        return commandExecuteResult;
    }
}
