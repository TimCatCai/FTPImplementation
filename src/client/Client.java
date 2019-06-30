package client;


public class Client {
    public static void start(String path) throws InterruptedException {
            System.out.println("Welcome to my ftp client -- by 蔡浩明");
            Controller controller = new Controller(new Display());
            controller.start(path);
    }
}
