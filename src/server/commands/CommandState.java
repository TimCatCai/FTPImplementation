package server.commands;

public class CommandState {
    private final String name;
    private final int stateValue;
    private CommandState nextCommandState;

    public CommandState(String name, int stateValue) {
        this.name = name;
        this.stateValue = stateValue;
        this.nextCommandState = null;
    }

    public void setNextCommandState(CommandState nextCommandState) {
        this.nextCommandState = nextCommandState;
    }

    public String getName() {
        return name;
    }

    public int getStateValue() {
        return stateValue;
    }

    public CommandState getNextCommandState() {
        return nextCommandState;
    }
}
