package app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import com.google.gson.Gson;
import comm.Receptor.OnMessageListener;
import comm.TCPConnectionServer;

public class Application implements OnMessageListener {

	private TCPConnectionServer connection;
	private ServerSocket server;

	public Application() {
		try {
			server = new ServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() {
		connection = new TCPConnectionServer(server);
		connection.setListenerOnMessage(this);
		connection.start();
	}

	@Override
	public void onMessage(String mss) {
		try {
			if (mss.equalsIgnoreCase("ip")) {
				InetAddress serverAddress = InetAddress.getLocalHost();
				String ip = serverAddress.getHostAddress();
				connection.getEmisor().sendMessage(ip);
			} else if (mss.equalsIgnoreCase("interface")) {
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface netN = interfaces.nextElement();
					if (netN.isUp()) {
						if (netN.getHardwareAddress() != null) {
							connection.getEmisor().sendMessage(netN.getName());
						}
					}
				}
			} else if (mss.equalsIgnoreCase("whattimeisit")) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                connection.getEmisor().sendMessage((dateFormat.format(date)));
				
			} else if (mss.equalsIgnoreCase("rtt")) {
				if (mss.getBytes().length == 1024) {
					connection.getEmisor().sendMessage(mss);
				}
			} else if (mss.equalsIgnoreCase("speed")) {
				if (mss.getBytes().length == 8192) {
					connection.getEmisor().sendMessage(mss);
				}
			}
			init();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
