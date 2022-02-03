package app;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import com.google.gson.Gson;

import communication.*;

public class App implements OnHandShake, OnInboxListener {

    private int port;
    private TCP tcp;
    private Gson gson;

    public App(int port) {
        this.port = port;
        tcp = TCP.getTCP(port);
    }

    public void createChannel() {

        System.out.println("No disponble");
        tcp.setHandShakeListener(this);
        tcp.setOnInboxListener(this);
        tcp.waitRequest(port);

    }

    @Override
    public String onMessage(String line) {
        line = line.toLowerCase();
        String[] parts = line.split("\n");
        line = parts[0];
        System.out.println(">>>" + line);
        String response = "";

        switch (line) {
            case "ip":
                List<String> ips = new ArrayList<>();
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface netN = interfaces.nextElement();

                        if (netN.isUp() && netN.getHardwareAddress() != null) { // On interfaces
                            List<InterfaceAddress> list = netN.getInterfaceAddresses();
                            for (int i = 0; i < list.size(); i++) {
                                ips.add("IP: " + list.get(i));
                            }
                        }
                    }
                } catch (SocketException e) {
                    response = "Error al obtener IP";
                    e.printStackTrace();
                }

                response = ips.toString();
                break;

            case "interface":

                Enumeration<NetworkInterface> interfaces;
                List<String> interList = new ArrayList<>();
                try {
                    interfaces = NetworkInterface.getNetworkInterfaces();

                    while (interfaces.hasMoreElements()) {
                        NetworkInterface netN = interfaces.nextElement();

                        if (netN.isUp()) { // On interfaces
                           interList.add(netN.getName());
                        }
                    }
                    response = interList.toString();

                } catch (SocketException e) {
                    response = "Error al obtener la Interfaz";
                    e.printStackTrace();
                }
                break;

            case "time":
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                response = (dateFormat.format(date));
                break;

            case "rtt":
                line = gson.toJson(new String[1024]);
                response = line;
                break;

            case "speed":
                line = gson.toJson(new String[8192]);
                response = line;
                break;

            default:
                response = "Error 404";
                break;
        }
        return response;
    }

    @Override
    public void onHandShake(boolean connected) {
        if (connected) {
            System.out.println("Conexion realizada");
        } else {
            System.out.println("******* Conexion Fallida *******");
        }
    }

}
