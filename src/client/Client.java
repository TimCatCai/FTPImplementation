package client;


public class Client {
    public static void main(String[] args) throws InterruptedException {
            Controller controller = new Controller(new Display());
            controller.start();
    }


}
