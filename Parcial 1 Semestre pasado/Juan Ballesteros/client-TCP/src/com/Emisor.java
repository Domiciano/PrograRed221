package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Emisor {
	

	private BufferedWriter writer;
	
	
	public Emisor(BufferedWriter bwriter) {
		this.writer = bwriter;
	}
	
	public void sendMessage(String msg) {
		new Thread(
				()->{
					try {
						writer.write(msg+"\n");
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		).start();
	}
	

}
