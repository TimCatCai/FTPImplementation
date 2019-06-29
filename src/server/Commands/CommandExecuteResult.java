package server.Commands;

import process.Event;

public class CommandExecuteResult {
    /**
     * next state of the command if the state of the command need to be modified, or null
     */
    private CommandState nextCommandState;

    private Event Operation;

    private Reply replyForCommand;

    public CommandExecuteResult(){

    }

    public CommandState getNextCommandState() {
        return nextCommandState;
    }

    public Event getOperation() {
        return Operation;
    }

    public Reply getReplyForCommand() {
        return replyForCommand;
    }

    public void setNextCommandState(CommandState nextCommandState) {
        this.nextCommandState = nextCommandState;
    }

    public void setOperation(Event operation) {
        Operation = operation;
    }

    public void setReplyForCommand(Reply replyForCommand) {
        this.replyForCommand = replyForCommand;
    }
}
