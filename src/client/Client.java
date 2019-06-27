package client;


import reposity.file.PathAccess;
import reposity.file.PathController;
import reposity.network.UserNetworkManagerImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) {
//        Path basePath = Paths.get("G:\\temp");
//
//        try {
//            Files.createDirectory(basePath);
//            Path tempPath = basePath.resolve("test.txt");
//            Files.createFile(tempPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("G:\\temp\\user.txt");
            UserNetworkManagerImp user = new UserNetworkManagerImp();
            user.openDataServerSocket(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}
