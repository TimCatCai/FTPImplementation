package server.Commands;

import server.Commands.definition.AbstractCommand;
import server.Commands.definition.CWD;
import server.Commands.definition.PASS;
import server.Commands.definition.USER;

import java.util.HashMap;

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

        commandsList.put("PASS",new PASS(
                "PASS",
                "PASS <SP> <password> <CRLF>\n" +
                        "<password> ::= <string>",
                1
        ));

        commandsList.put("CWD", new CWD(
                "CWD",
                "<SP> <pathname> <CRLF>\n"
                         + "<pathname> ::= <string>",
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
