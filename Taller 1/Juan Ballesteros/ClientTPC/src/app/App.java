package app;

import communication.*;

public class App implements OnHandShake, OnInboxListener {
    private String ip;
    private int port;

    private TCP tcp;

    public App(String ip, int port) {
        this.ip = ip;
        this.port = port;
        tcp = TCP.getTCP();
    }

    public void request(String request){
        tcp.setHandShakeListener(this);
        tcp.setOnInboxListener(this);

        tcp.sendRequest(ip, port, request);
    }

    public void request(String[] request){
        tcp.setHandShakeListener(this);
        tcp.setOnInboxListener(this);

        tcp.sendRequestFile(ip, port, request);
    }

    @Override
    public void onMessage(String line) {
        System.out.println("<<   " + line + "\n");      
    }

    @Override
    public void onHandShake(boolean connected) {
        if(connected){
            System.out.println("Conexion realizada");
        }else{
            System.out.println("******* Conexion Fallida *******");
        }
        
    }
}
