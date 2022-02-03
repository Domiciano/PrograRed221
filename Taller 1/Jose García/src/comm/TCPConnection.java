package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPConnection extends Thread{
	//Singleton
	private static TCPConnection instance=null;
	private TCPConnection() {
		
	}
	public static synchronized TCPConnection getInstance() {
		if(instance==null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	//-------------------------------------------
	private Socket socket;
	private int puerto;
	private String ip;
	private String option;
	
	
	public OnMessageListener listener;
	
	
	public void setPuerto(int puerto) {
		this.puerto=puerto;
	}

	@Override
	public void run() {
		try {
			System.out.println("Cliente conectando con servidor");
			socket=new Socket(ip,puerto);
			System.out.println("Servidor alcanzado");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream ()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw.write(option+"\n");
			bw.flush();
			String alfa;
			long start, end;
			switch (option) {
			case "remotelconfig":
				String msg =reader.readLine();
				listener.onMessage("Server IP Address: "+msg);
				break;
			case "interface":
				listener.onMessage("Server Interface: "+reader.readLine());
				break;
			case "whatTimeIsIt":
				listener.onMessage("Server Time: "+reader.readLine());
				break;
			case "RTT":
				alfa = new String(new byte[1024]);
				start = System.currentTimeMillis();
				bw.write(alfa+"\n");
				bw.flush();
				alfa = reader.readLine();
				end = System.currentTimeMillis();
				listener.onMessage("Time taken to send and receive 1024 bytes to and from server: "+(end-start)+" ms");
				break;
			case "speed":
				alfa = new String(new byte[8192]);
				start = System.currentTimeMillis();
				bw.write(alfa+"\n");
				bw.flush();
				alfa = reader.readLine();
				end = System.currentTimeMillis();
				double speed=16384/(end-start);
				listener.onMessage("Speed: "+speed+" Kb/s");
				break;
			case "shutDown":
				bw.write(option+"\n");
				bw.flush();
				break;
			default:
				break;
			}
			bw.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void setListener(OnMessageListener listener) {
		this.listener=listener;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public interface OnMessageListener{
		public void onMessage(String msg);
	}

	public void setOption(String i) {
		option=i;
	}
}
