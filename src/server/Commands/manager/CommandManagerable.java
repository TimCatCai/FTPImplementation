package server.Commands.manager;

import server.Commands.definition.AbstractCommand;
import server.Commands.CommandExecuteResult;

public interface CommandManagerable {

    CommandExecuteResult parse(AbstractCommand command);
}
