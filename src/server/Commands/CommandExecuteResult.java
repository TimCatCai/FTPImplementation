package server.Commands;

import process.Event;

public class CommandExecuteResult {
    /**
     * next state of the command if the state of the command need to be modified, or null
     */
    private State nextState;

    private Event Operation;

    private Reply replyForCommand;

    public CommandExecuteResult(){

    }

    public State getNextState() {
        return nextState;
    }

    public Event getOperation() {
        return Operation;
    }

    public Reply getReplyForCommand() {
        return replyForCommand;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public void setOperation(Event operation) {
        Operation = operation;
    }

    public void setReplyForCommand(Reply replyForCommand) {
        this.replyForCommand = replyForCommand;
    }
}
