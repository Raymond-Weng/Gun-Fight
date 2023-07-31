package main.connect;

import jGame.loop.timer.Timer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Timer {
    private ServerSocket serverSocket = null;
    private Socket socket;

    private String ip;
    private int port;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String syncString = "good morning";
    private String readString = "";
    private int pingMilliSecond = 0;

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
                System.out.println(socket.getInetAddress());
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            bufferedWriter.write(System.currentTimeMillis() + "!" + syncString);
            bufferedWriter.flush();
            String s = bufferedReader.readLine();
            pingMilliSecond = Integer.valueOf(s.split("!")[0]);
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
}
