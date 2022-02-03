package app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;

import comm.Receptor.OnMessageLitener;
import comm.TCPConnection;

public class Application implements OnMessageLitener{

	private TCPConnection connection;
	private ServerSocket server;

	public Application() {
		try {
			server = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		connection = new TCPConnection(server);	
		connection.setListenerOnMessage(this);
		connection.start();
	}

	@Override
	public void onMessage(String msg) {
		try {
			if (msg.equalsIgnoreCase("remoteipconfig")) {
				InetAddress serverAddress = InetAddress.getLocalHost();
				String ip = serverAddress.getHostAddress();
				connection.getEmisor().sendMessage(ip);
			}
			else if(msg.equalsIgnoreCase("interface")) {
				Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface netN = (NetworkInterface) interfaces.nextElement();
					if (netN.isUp()) {
						Enumeration<InetAddress> address = netN.getInetAddresses();
						while(address.hasMoreElements()) {
							InetAddress test = address.nextElement();
							InetAddress serverAddress = InetAddress.getLocalHost();
							if (test.getHostAddress().equals(serverAddress.getHostAddress())) {
								connection.getEmisor().sendMessage(netN.getName());
							}
						}
					}
				}
			}
			else if(msg.equalsIgnoreCase("whattimeisit")) {
				Calendar c = Calendar.getInstance();
				String fecha = c.getTime().toString();
				connection.getEmisor().sendMessage(fecha);
			}
			else if(msg.getBytes().length == 1024) {
				connection.getEmisor().sendMessage(msg);
			}
			else if(msg.getBytes().length == 8192) {
				connection.getEmisor().sendMessage(msg);
			}
			init();
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
	}
}