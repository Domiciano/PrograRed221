package main;

import java.io.IOException;

import app.Application;

public class Main {

	
	private static Application app;

	public static void main(String[] args) throws IOException {
		app = new Application() ;
		menu();	
		app.init();
	}
	
	public static void menu() {
		System.out.println();
        System.out.println("select a instruction:");
        System.out.println("ip");
        System.out.println("interface");
        System.out.println("whattimeisit");
        System.out.println("RTT");
        System.out.println("speed");
	}
}
