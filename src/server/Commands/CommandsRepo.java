package server.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandsRepo {
    private HashMap<String,AbstractCommand> commandsList  = new HashMap<>();
    private static CommandsRepo commandsRepo;
    private CommandsRepo(){
        commandsList.put("USER",new USER(
                "USER",
            " USER <SP> <username> <CRLF>\n" +
                        "<username> ::= <string>",
      1
                ));



    }

    public static AbstractCommand getCommand(String name){
        if(commandsRepo == null){
            commandsRepo = new CommandsRepo();
        }
        return commandsRepo.getCommandInList(name);
    }

    protected AbstractCommand getCommandInList(String name){
        return commandsList.get(name);
    }
}
