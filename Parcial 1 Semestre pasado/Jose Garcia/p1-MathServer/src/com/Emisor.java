package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Emisor {

	private BufferedWriter bwriter;

	public Emisor(BufferedWriter bwriter) {
		this.bwriter = bwriter;
	}

	public void sendMessage(String msg) {
		if (msg != null)
			new Thread(() -> {
				try {
					bwriter.write(msg + "\n");
					bwriter.flush();
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("User disconnected. sessions: "+	TCPConnectionServer.getInstance().getNumberOfSessions());
				}
			}).start();
	}

}
