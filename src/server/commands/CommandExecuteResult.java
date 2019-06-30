package server.commands;

import process.event.Event;

public class CommandExecuteResult {
    private Event Operation;

    private Reply replyForCommand;

    public CommandExecuteResult(){

    }
    public Event getOperation() {
        return Operation;
    }

    public Reply getReplyForCommand() {
        return replyForCommand;
    }

    public void setOperation(Event operation) {
        Operation = operation;
    }

    public void setReplyForCommand(Reply replyForCommand) {
        this.replyForCommand = replyForCommand;
    }
}
