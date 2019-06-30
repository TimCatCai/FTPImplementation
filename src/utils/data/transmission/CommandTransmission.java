package utils.data.transmission;

import server.commands.definition.AbstractCommand;
import server.commands.definition.USER;

import java.util.Arrays;
import java.util.logging.Logger;

public class CommandTransmission {

    public static  AbstractCommand spiltNewCommandStringToCommand(String data,String regx, Logger logger){
        String [] commandString = splitCommandString(data,regx);
        String [] parameters = null ;
        int [] parameterNumber = new int[1] ;
        if(commandString.length >= 2){
            parameters = Arrays.copyOfRange(commandString,1,commandString.length);
            parameterNumber[0] = parameters.length;
        }else{
            parameterNumber[0] = 0;
        }

        logger.info("commandString length: " + commandString.length);

        for(String command :commandString){
           logger.info( command + " " );
        }
        System.out.println();
        return new USER(commandString[0].toUpperCase(),
                "",
                parameterNumber,
                parameters);
    }

    public static String [] splitCommandString(String commandString,String regex){
       return commandString == null ? null : commandString.split(regex);
    }


}
