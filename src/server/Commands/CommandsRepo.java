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
      1,
                null
                ));

        commandsList.put("PASS",new PASS(
                "PASS",
                "PASS <SP> <password> <CRLF>\n" +
                        "<password> ::= <string>",
                1,
                null
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
