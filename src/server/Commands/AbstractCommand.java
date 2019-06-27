package server.Commands;

/**
 *
 */
public abstract class AbstractCommand {
    protected final String name;
    protected String description;
    protected int parameterNumber;
    protected State currentSate;
    protected State firstState;

    protected AbstractCommand(String name, String description, int parameterNumber) {
        this.name = name;
        this.description = description;
        this.parameterNumber = parameterNumber;
    }

    public abstract boolean execute(String [] parameters);


    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {

        return name;
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
}
