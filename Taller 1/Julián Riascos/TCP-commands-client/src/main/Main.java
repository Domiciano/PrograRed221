package main;

import java.io.IOException;

import app.Application;

public class Main {
	public static void main(String[] args) {
		Application app = new Application();
			try {
				app.init();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}	
	}
}
