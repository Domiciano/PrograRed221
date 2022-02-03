package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.App;

public class Main {

    private static boolean exit;
    private static BufferedReader br;

    private static App app;

    public static void main(String[] args) {
        app = new App("2.tcp.ngrok.io", 10468); // ngrok

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        exit = false;

        System.out.println("--------x Welcome to TCP App x--------");

        menu();

        while (!exit) {
            String line = br.readLine();
            checkIn(line);
            if (!exit) {
                menu();
            } else {
                System.out.println(">> Adios");
            }
        }
    }

    private static void menu() {
        System.out.println();
        System.out.println(">> Supported instructions:");
        System.out.println("      remoteIpConfig");
        System.out.println("      interface");
        System.out.println("      whatTimeIsIt");
        System.out.println("      RTT");
        System.out.println("      speed");
        System.out.println(">> To exit the app type: close \n");
    }

    private static void checkIn(String line) {
        line = line.toLowerCase();
        switch (line) {
            case "remoteipconfig":
                app.request("ip");
                return;

            case "interface":
                app.request("interface");
                return;

            case "whattimeisit":
                app.request("time");
                return;

            case "rtt":
                app.request(new String[1024]);
                break;

            case "speed":
                app.request(new String[8192]);
                return;

            case "close":
                exit = true;
                return;

            default:
                System.out.println("Ingresa un valor valido \n");
                return;

        }
    }

}
