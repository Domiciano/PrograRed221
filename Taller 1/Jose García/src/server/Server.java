package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {

	public static void main(String[] args) {
		ServerSocket ssocket;

		try {
			int port = 5000;
			ssocket = new ServerSocket(port);
			boolean loop = true;
			while (loop) {
				// System.out.println("Connecting...");
				Socket server = ssocket.accept();
				// System.out.println("Client connected");

				BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
				String line = "", alfa;
				while (line.equals("")) {
					line = reader.readLine();
				}
				InetAddress myAddress = InetAddress.getLocalHost();
				switch (line) {
				case "remotelconfig":
					writer.write(myAddress.getHostAddress());
					break;
				case "interface":
					NetworkInterface net = NetworkInterface.getByInetAddress(myAddress);
					writer.write("" + net);
					break;
				case "whatTimeIsIt":
					LocalDateTime time = java.time.LocalDateTime.now();
					writer.write(time.toString());
					break;
				case "RTT":
					alfa = reader.readLine();
					writer.write(alfa);
					break;
				case "speed":
					alfa = reader.readLine();
					writer.write(alfa);
					break;
				case "shutDown":
					loop = false;
					writer.close();
					reader.close();
					server.close();
					break;
				default:
					break;
				}
				if (loop)
					writer.flush();
				server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
