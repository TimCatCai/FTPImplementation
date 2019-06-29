package server.commands.definition;

import client.UserStateable;
import server.commands.CommandExecuteResult;

/**
 *
 */
public abstract class AbstractCommand {
    protected final String name;
    protected String description;
    protected int [] parameterNumber;
    protected String [] parameters;

    protected AbstractCommand(String name, String description, int [] parameterNumber,String [] parameters) {
        this.name = name;
        this.description = description;
        this.parameterNumber = parameterNumber;
        this.parameters = parameters;
    }

    protected AbstractCommand(String name, String description, int [] parameterNumber) {
        this(name,description,parameterNumber,null);
    }
    public abstract CommandExecuteResult execute(String [] parameters,int [] parameterNumber, UserStateable userState);


    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {

        return name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getDescription() {
        return description;
    }

    public int [] getParameterNumber() {
        return parameterNumber;
    }

}
