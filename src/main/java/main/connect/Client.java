package main.connect;

import jGame.loop.timer.Timer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Timer {
    private Socket socket = null;

    private volatile String host;
    private volatile int port;

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private volatile String syncString = "";
    private volatile String readString = "";
    private volatile int pingMilliSecond = 0;

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
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String s = dataInputStream.readUTF();
            pingMilliSecond = (int)(Double.valueOf(s.split("!")[0]) - System.currentTimeMillis());
            readString = s.substring(s.split("!")[0].length() + 1);

            dataOutputStream.writeUTF(System.currentTimeMillis() + "!" + syncString);
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
}
