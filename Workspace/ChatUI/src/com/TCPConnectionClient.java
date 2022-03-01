package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPConnectionClient extends Thread {

	private Socket socket;
	private Sender sender;
	
	@Override
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 6000);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			sender = new Sender(writer);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
