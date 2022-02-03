package app;

import comm.Receptor.OnMessageListener;

import java.util.Scanner;

import comm.Emisor;
import comm.TCPConnectionClient;

public class Application extends Thread implements OnMessageListener {

	private TCPConnectionClient connection;

	public Application() {
		connection = new TCPConnectionClient();
		connection.setListener(this);
		connection.setPort(5000);
		connection.setIp("127.0.0.1");
	}

	public TCPConnectionClient connection() {
		TCPConnectionClient conect = new TCPConnectionClient();
		conect.setListener(this);
		conect.setPort(12920);
		conect.setIp("127.0.0.1");
		return conect;
	}

	public void init() {
		Scanner scanner = new Scanner(System.in);
		double inicio;
		connection.start();

		while (true) {
			String line = scanner.nextLine();
			Emisor emisor = connection.getEmisor();
			if (emisor != null) {

				switch (line) {

				case "rtt":
					String a = new String(new byte[1024]);
					inicio = System.nanoTime();
					emisor.sendMessage(a);
					connection = connection();
					connection.start();

					break;

				case "speed":
					String b = new String(new byte[1024]);
					inicio = System.nanoTime();
					emisor.sendMessage(b);
					connection = connection();
					connection.start();

					break;

				default:
					emisor.sendMessage(line);
					connection = connection();
					connection.start();
					break;
				}
			} else {
				System.out.println("no clients conected");
			}
		}
	}

	@Override
	public void onMessage(String mss) {
		System.out.println("delivered " + mss);
	}

}
