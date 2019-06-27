package server.Commands;

public interface CommandManagerable {

    Reply parse(AbstractCommand command);
}
