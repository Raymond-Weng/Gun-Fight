package main.connect;

import jGame.loop.timer.Timer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Timer {
    private ServerSocket serverSocket = null;
    private Socket socket;

    private volatile String ip;
    private volatile int port;

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private volatile String syncString = "";
    private volatile String readString = "";
    private volatile int pingMilliSecond = 0;

    public Server() {
        this(0);
    }

    public Server(int port) {
        super(1d / 60d);
        this.port = port;
    }

    @Override
    public void action() {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
                if (port == 0) {
                    ip = InetAddress.getLocalHost().getHostAddress();
                    port = serverSocket.getLocalPort();
                }
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress());    //TODO a debug print here
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            dataOutputStream.writeUTF(System.currentTimeMillis() + "!" + syncString);

            String s = dataInputStream.readUTF();
            pingMilliSecond = (int)(Double.valueOf(s.split("!")[0]) - System.currentTimeMillis());
            readString = s.substring(s.split("!")[0].length() + 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSyncString(String s){
        this.syncString = s;
    }

    public String getReadString(){
        return readString;
    }

    public int getPingMilliSecond(){
        return pingMilliSecond;
    }

    public String getIp(){
        return ip;
    }

    public int getPort(){
        return port;
    }
}
