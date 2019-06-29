package server.commands;

import server.commands.definition.*;

import java.util.HashMap;

public class CommandsRepo {
    private HashMap<String,AbstractCommand> commandsList  = new HashMap<>();
    private static CommandsRepo sCommandsRepo;
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
                "CWD <SP> <pathname> <CRLF>\n"
                         + "<pathname> ::= <string>",
                1
        ));

        commandsList.put("CDUP",
                new CDUP("CDUP",
                        " CDUP <CRLF> ",
                        0));
        commandsList.put("HELP",
                new HELP(
                        "HELP",
                        " List all commands have implemented\n ",
                0
                ));


    }

    public static CommandsRepo getInstance(){
        if(sCommandsRepo == null){
            sCommandsRepo = new CommandsRepo();
        }
        return sCommandsRepo;
    }
    public static AbstractCommand getCommand(String name){

        return getInstance().getCommandInList(name);
    }

    private AbstractCommand getCommandInList(String name){
        return commandsList.get(name);
    }

    private HashMap<String,AbstractCommand> getCommandsList(){
        return this.commandsList;
    }

    public static String seeAllCommands(){

        StringBuilder builder = new StringBuilder();
        HashMap<String,AbstractCommand> commandsList = getInstance().getCommandsList();
        AbstractCommand command;
        for(String key: commandsList.keySet()){
           command = commandsList.get(key);
            builder.append(command.getName());
            builder.append("\n");
            builder.append(command.getDescription());
            builder.append("\n");
        }
        return builder.toString();
    }
}
