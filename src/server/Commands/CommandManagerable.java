package server.Commands;

public interface CommandManagerable {

    CommandExecuteResult parse(AbstractCommand command);
}
