package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		// Estar pendiente de las conexion
		try {
			ServerSocket server = new ServerSocket(6000);
			while (true) {
				System.out.println("Esperando...");
				Socket socket = server.accept();
				System.out.println("Cliente Conectado");

				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(is));

				while (true) {
					System.out.println("Esperando mensaje...");
					String line = reader.readLine();
					System.out.println(line);
					if (line == null)
						break;
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
