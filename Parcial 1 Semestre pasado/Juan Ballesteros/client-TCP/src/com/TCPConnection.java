package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPConnection extends Thread {

	private static TCPConnection instance = null;

	private TCPConnection() {}

	public static synchronized TCPConnection getInstance() {

		if (instance == null) {
			instance = new TCPConnection();
		}

		return instance;
	}

	private Socket socket;
	private String ip;
	private int puerto;
	private Receptor receptor;
	private Emisor emisor;
	private OnConnectionListener connectionListener;

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public void run() {
		try {

			System.out.println("Conectado al servidor");
			socket = new Socket(ip, puerto);
			System.out.println("Conectado");
			connectionListener.onConnection(true);

			receptor = new Receptor(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			receptor.start();

			emisor = new Emisor(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

		} catch (IOException e) {
			connectionListener.onConnection(false);
			e.printStackTrace();
		}
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public Receptor getReceptor() {
		return receptor;
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public interface OnConnectionListener {
		public void onConnection( boolean connected);
	}

}
