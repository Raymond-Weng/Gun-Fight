package main.connect;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("localhost", 55462);
        client.action();
    }
}
