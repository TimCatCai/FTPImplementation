package server.Commands;

public class CommandManager implements CommandManagerable {


    @Override
    public Reply parse(AbstractCommand command) {
        AbstractCommand realCommand = CommandsRepo.getCommand(command.getName());
        if(realCommand == null){
            return ReplyRepo.getReply(ReplyRepo.NO_SUCH_COMMAND);
        }else{
             return realCommand.execute(command.getParameters());
        }
    }
}
