package main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import app.App;
import communication.Responder;
import communication.TCP;

public class Main implements Responder{

    public static int PORT = 5000;

    public static void main(String[] args) {
        new Main();
    }


    private TCP tcp;
    private App app;

    public Main(){
        System.out.println("--------x Welcome to TCP Server App x--------");

        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        app = new App(PORT);
        tcp = TCP.getTCP(PORT);
        tcp.setResponderListener(this);
        
        app.createChannel();     
        
    }

    @Override
    public void available() {
        app.createChannel();
    }
}
