package client;


import process.*;
import reposity.network.ConnectorNetworkManagerImp;
import reposity.network.ProviderNetworkManagerImp;

import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws InterruptedException {
            Controller controller = new Controller(new Display());
            controller.start();
    }


}
