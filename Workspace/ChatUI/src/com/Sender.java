package com;

import java.io.BufferedWriter;
import java.io.IOException;

public class Sender {

	private BufferedWriter writer;

	public Sender(BufferedWriter writer) {
		this.writer = writer;
	}

	public void send(String msg) {
		new Thread(() -> {
			try {
				writer.write(msg + "\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

}
