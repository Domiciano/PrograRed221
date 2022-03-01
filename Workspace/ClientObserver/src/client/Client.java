package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import events.OnConnectionListener;

public class Client implements OnConnectionListener{

	public static void main(String[] args) {

		Client c = new Client();

	}
	
	//Globales
	private TCPConnection connection;

	public Client() {
		
		connection = new TCPConnection();
		connection.setListener(this);
		connection.start();
		
		init();

	}
	
	public void init() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			//Envio
			connection.sendMessage(line);
		}
		
		
	}
	

	@Override
	public void onResult(String result) {
		System.out.println(result);
	}

}
