package server.Commands;

import client.UserState;
import client.UserStateable;

/**
 *
 */
public abstract class AbstractCommand {
    protected final String name;
    protected String description;
    protected int parameterNumber;
    protected String [] parameters;
    protected CommandState currentSate;
    protected CommandState firstCommandState;
    protected int successCode;
    protected int failureCode;

    protected AbstractCommand(String name, String description, int parameterNumber,String [] parameters) {
        this.name = name;
        this.description = description;
        this.parameterNumber = parameterNumber;
        this.parameters = parameters;
    }

    public abstract CommandExecuteResult execute(String [] parameters, UserStateable userState);


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

    public int getParameterNumber() {
        return parameterNumber;
    }

    public CommandState getCurrentSate() {
        return currentSate;
    }

    public CommandState getFirstCommandState() {
        return firstCommandState;
    }

    public int getSuccessCode() {
        return successCode;
    }

    public int getFailureCode() {
        return failureCode;
    }

    public void setCurrentSate(CommandState currentSate) {
        this.currentSate = currentSate;
    }
}
