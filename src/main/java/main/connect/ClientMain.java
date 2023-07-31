package main.connect;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("localhost", 51951);
        client.action();
    }
}
