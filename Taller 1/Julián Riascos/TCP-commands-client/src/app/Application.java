package app;

import java.io.IOException;
import java.util.Scanner;

import comm.Emisor;
import comm.Receptor.OnMessageListener;
import comm.TCPconnection;

public class Application extends Thread implements OnMessageListener {

	private TCPconnection connection;
	public static double inicio;

	public Application() {
		connection = new TCPconnection();
		connection.setListenerOfMessages(this);
		connection.setPuerto(12920);
		connection.setIp("0.tcp.ngrok.io");
	}

	public TCPconnection newConnection() {
		TCPconnection reconnect = new TCPconnection();
		reconnect.setListenerOfMessages(this);
		reconnect.setPuerto(12920);
		reconnect.setIp("0.tcp.ngrok.io");
		return reconnect;
	}
	
	@SuppressWarnings({ "resource" })
	public void init() throws InterruptedException, IOException {
		Scanner lector = new Scanner(System.in);
		connection.start();
		while(true) {
			String line = lector.nextLine();
			Emisor m = connection.getEmisor(); 
			if(m!=null) { 
				if (line.equalsIgnoreCase("RTT")) {
					String alfa = new String(new byte[1024]);
					inicio = System.nanoTime();
					m.sendMessage(alfa);
					connection = newConnection();
					connection.start();
				}
				else if(line.equalsIgnoreCase("speed")) {
					String alfa = new String(new byte[8192]);
					inicio = System.nanoTime();
					m.sendMessage(alfa);
					connection = newConnection();
					connection.start();
				}
				else {
					m.sendMessage(line);
					connection = newConnection();
					connection.start();
				}
			}
			else {
				System.out.println("Ningún cliente se ha conectado aún");
			}
		}
	}

	@Override
	public void onMessage(String msg) {
		System.out.println("Recibido: " + msg);
		if(msg.getBytes().length==1024) {
			double fin = System.nanoTime();
			double finalTime = (fin - inicio)/1000000000;
			System.out.println("Se demoró " + finalTime + " segundos");
		}
		else if(msg.getBytes().length==8192) {
			double fin = System.nanoTime();
			double finalTime = (fin - inicio)/1000000000;
			double speed = (8192/1024)/finalTime;
			System.out.println("La velocidad es de " + speed + " kb/s");
		}
	}
	
}
