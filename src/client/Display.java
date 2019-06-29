package client;

import java.util.Scanner;

public class Display implements ViewInterface {
    private Scanner  scanner = new Scanner(System.in);
    @Override
    public void display(String data) {
        System.out.println("ftp >" + data);
    }

    @Override
    public String read(String hint) {
        display(hint);
        return scanner.nextLine();
    }
}
