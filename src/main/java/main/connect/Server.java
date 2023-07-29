package main.connect;

import jGame.loop.timer.Timer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Timer {
    private ServerSocket serverSocket = null;
    private Socket socket;

    private String ip;
    private int port;

    public Server(){
        this(0);
    }

    public Server(int port){
        super(1d/60d);
        this.port = port;
    }

    @Override
    public void action() {
        if(serverSocket == null){
            try {
                serverSocket = new ServerSocket(port);
                if(port == 0){
                    ip = InetAddress.getLocalHost().getHostAddress();
                    port = serverSocket.getLocalPort();
                }
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
