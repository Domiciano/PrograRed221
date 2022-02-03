package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Enumeration;

public class TCPConnection extends Thread{

	private ServerSocket server;
	private Socket socket;
	private int puerto;
	private static TCPConnection instance = null;

	private TCPConnection() {

	}
	public static synchronized TCPConnection getInstance() 	{	
		if(instance == null) {

			instance = new TCPConnection();
		}
		return instance;

	}

	public void setPuerto(int puerto) {	
		this.puerto = puerto;
	}

	@Override
	public void run() {
		try {

			server = new ServerSocket(puerto);

			while(true) {

				System.out.println("Esperando la solicitud de conexion del cliente..");
				socket = server.accept();
				System.out.println("La conexion con el cliente se ha establecido exitosamente");
				
				//Declaracion de envio y recepcion

				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bwritter = new BufferedWriter(osw);

				String line = breader.readLine();

				if(line != null) {
					System.out.println("Mensaje del cliente: "+line);

					switch(line) {
					case "interface":

						Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
						String intI = "Interfaces: ";
						int i = 1;
						
						while (interfaces.hasMoreElements()) {
							NetworkInterface netN = interfaces.nextElement();
							if(netN.isUp()) {

								intI += i+". "+netN.getName()+" ";				
                                i++;
							}
						}

						bwritter.write(intI+"\n");
						bwritter.flush();
						break;

					case "speed":

						String speedL = breader.readLine();				
						bwritter.write("Devolviendo mensaje: "+speedL+"\n");
						bwritter.flush();
						break;

					case "RTT":

						String rttL = breader.readLine();				
						bwritter.write("Delvoviendo mensaje: "+rttL+"\n");
						bwritter.flush();
						break;


					case "whatTimeIsIt":

						Calendar c = Calendar.getInstance();
						String date = c.getTime().toString();

						bwritter.write(date+"\n");
						bwritter.flush();
						break;

					case "remoteIpconfig":

						String ipSend = InetAddress.getLocalHost().getHostAddress();					
						bwritter.write(ipSend+"\n");
						bwritter.flush();
						break;

					default:

						bwritter.write("Comando desconocido o invalido"+"\n");
						bwritter.flush();
						break;

					}


					socket.close();
					System.out.println("El server ha cerrado la conexion");
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}


	}


}