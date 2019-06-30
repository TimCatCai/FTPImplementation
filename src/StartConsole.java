import client.Client;
import server.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class StartConsole {

    public static void main(String []args) throws IOException, InterruptedException {
        System.out.println("Select mode:  Server or Client(Input server or client to select)");
        Scanner in = new Scanner(System.in);
        String  mode ;
        boolean isServer;
        while ( true ){
          mode = in.nextLine();
          if("client".equals(mode.toLowerCase()) ){
              isServer = false;
              break;
          }else if ("server".equals(mode.toLowerCase())) {
              isServer = true;
              break;
          }
            System.out.println("Select mode:  Server or Client(Input server or client to select)");
        }


        if(isServer) {
            System.out.println("Server has started");
            Server.start();
        }else{
            System.out.println("In order to test, select local ip address: 127.0.0.1");
            while (true){
                System.out.println("Input directory to store files copied from server");
                System.out.println("If input nothing, select user current directory");
                mode = in.nextLine();
                if(mode.length() == 0 || Files.exists(Paths.get(mode))){
                    break;
                }
                System.out.println("Directory is invalid");
            }
            Client.start(mode);

        }
    }
}
