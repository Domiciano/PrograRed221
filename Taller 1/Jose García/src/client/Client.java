package client;

import java.util.Scanner;
import comm.*;
import comm.TCPConnection.OnMessageListener;

public class Client implements OnMessageListener {

	private TCPConnection connection;

	public void init() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Write a command");
			System.out.println("type remotelconfig to get server ip");
			System.out.println("type interface to get the interface the server is using");
			System.out.println("type RTT to know how long it takes to send and receive a 1024 byte message");
			System.out.println(
					"type speed to know the speed of the connection based on how long it takes to send and receive 8192 bytes");
			System.out.println("type shutDown to end application");
			String line = sc.nextLine();
			if (line.equals("remotelconfig") || line.equals("interface") || line.equals("whatTimeIsIt")
					|| line.equals("RTT") || line.equals("speed") || line.equals("shutDown")) {
				initiateConnection(line);
				if (line.equals("shutDown"))
					break;
			}

		}
		sc.close();
	}

	private void initiateConnection(String i) {
		try {
			connection = TCPConnection.getInstance();
			//connection.setPuerto(13225);

			connection.setPuerto(5000);
			//connection.setIp("6.tcp.ngrok.io");
			connection.setIp("127.0.0.1");
			connection.setListener(this);
			connection.setOption(i);
			connection.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onMessage(String msg) {
		System.out.println("->"+msg);
	}
}
