package server.Commands;

public class State {
    private final String name;
    private final int stateValue;
    private State nextState;

    public State(String name, int stateValue) {
        this.name = name;
        this.stateValue = stateValue;
        this.nextState = null;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public String getName() {
        return name;
    }

    public int getStateValue() {
        return stateValue;
    }

    public State getNextState() {
        return nextState;
    }
}
