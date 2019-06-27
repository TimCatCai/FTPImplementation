package server;

import process.*;
import reposity.network.NetworkManager;
import reposity.network.ProviderNetworkManagerImp;
import server.Commands.*;

import java.io.IOException;
import java.net.Socket;

public class ServerProcessThread implements Runnable , ServerProcessable {
    private Socket socketToUser;
    private DataTransferProcessable commandsTransferProcess;
    private CommandManagerable commandManager;
    private ServerProcessable PIInterface;
    private EventQueue serverProcessThreadEventQueue;

    private static int SERVER_PI_DEFAULT_PORT = 21;

    public ServerProcessThread(Socket socketToUser) {
        this.socketToUser = socketToUser;
        NetworkManager serverPINetworkManager = new ProviderNetworkManagerImp(SERVER_PI_DEFAULT_PORT);
        PIInterface = new ProtocolInterperImp();
        commandsTransferProcess = new DataTransferProcess(serverPINetworkManager, PIInterface);
        commandManager = new CommandManager();

        serverProcessThreadEventQueue = new EventQueue();
    }

    @Override
    public void run() {

        AbstractCommand commandReceived;
        Reply tempReply;
        StringEvent tempEvent;
        while (!socketToUser.isClosed()) {
            acceptCommand();
            /**
             * @TODO to block
             */
            commandReceived = new USER("", "", 1, null);
            tempReply = commandManager.parse(commandReceived);
            tempEvent = new StringEvent(tempReply.toString(), DataDirection.SENT);
            commandsTransferProcess.sentData(tempEvent);

        }

        try {
            socketToUser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptCommand() {
        Event stringEvent = new StringEvent("", DataDirection.RECEIVE);
        this.commandsTransferProcess.receiveData(stringEvent);
    }

    @Override
    public void replyOperationResult(Event response) {
        serverProcessThreadEventQueue.addEvent(response);
    }
}
