package server.commands.manager;

import server.commands.definition.AbstractCommand;
import server.commands.CommandExecuteResult;

public interface CommandManagerable {

    CommandExecuteResult parse(AbstractCommand command);
}
