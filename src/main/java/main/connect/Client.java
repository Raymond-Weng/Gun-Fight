package main.connect;

import jGame.loop.timer.Timer;

import java.io.IOException;
import java.net.Socket;

public class Client extends Timer {
    private Socket socket = null;

    private String host;
    private int port;

    public Client(String host, int port){
        super(1d/60d);
        this.host = host;
        this.port = port;
    }

    @Override
    public void action() {
        if(socket == null){
            try {
                socket = new Socket(host, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
