package main.connect;

import jGame.loop.timer.Timer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Timer {
    private Socket socket = null;

    private String host;
    private int port;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String syncString = "";
    private String readString = "";
    private int pingMilliSecond = 0;

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
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String s = bufferedReader.readLine();
            pingMilliSecond = Integer.valueOf(s.split("!")[0]);
            readString = s.substring(s.split("!")[0].length() + 1);

            bufferedWriter.write(System.currentTimeMillis() + "!" + syncString);
            bufferedWriter.flush();

            System.out.println(pingMilliSecond + readString);
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
