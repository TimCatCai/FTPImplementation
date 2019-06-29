package server.Commands;

/**
 *
 */
public abstract class AbstractCommand {
    protected final String name;
    protected String description;
    protected int parameterNumber;
    protected String [] parameters;
    protected State currentSate;
    protected State firstState;
    protected int successCode;
    protected int failureCode;

    protected AbstractCommand(String name, String description, int parameterNumber,String [] parameters) {
        this.name = name;
        this.description = description;
        this.parameterNumber = parameterNumber;
        this.parameters = parameters;
    }

    public abstract CommandExecuteResult execute(String [] parameters);


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

    public State getCurrentSate() {
        return currentSate;
    }

    public State getFirstState() {
        return firstState;
    }

    public int getSuccessCode() {
        return successCode;
    }

    public int getFailureCode() {
        return failureCode;
    }
}
