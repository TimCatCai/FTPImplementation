package server.Commands;

public class USER extends AbstractCommand {
    private final String FIRST_STATE = "first time";
    private final int FIRST_STATE_VALUE = 0;
    private final String ALREADY_REGISTER_STATE = "register";
    private final int ALREADY_REGISTER_STATE_VALUE = 1;

    public USER(String name, String description, int parameterNumber) {
        super(name, description,parameterNumber);

        State temp;
        firstState = new State(FIRST_STATE,FIRST_STATE_VALUE);
        temp  = new State(ALREADY_REGISTER_STATE,ALREADY_REGISTER_STATE_VALUE);
        firstState.setNextState(temp);
        currentSate = firstState;
    }

    @Override
    public boolean execute(String [] parameters) {


        return false;
    }
}
