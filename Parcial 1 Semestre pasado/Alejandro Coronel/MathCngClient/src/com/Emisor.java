package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Emisor {
	

	private BufferedWriter bwriter;
	
	
	public Emisor(BufferedWriter bwriter) {
		this.bwriter = bwriter;
	}
	
	
	public void sendMessage(String msg) {
		new Thread(
				()->{
					try {
						bwriter.write(msg+"\n");
						bwriter.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		).start();
	}
	

}
