package main;

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

public class Servidor {

	public static void main(String[] args) {

		try {

			
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(5000);

			while(true) {

				System.out.println("(Server) Waiting for connection...");
				Socket socket =	server.accept();
				System.out.println("(Server) Connected");

				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bwritter = new BufferedWriter(osw);

				String line = breader.readLine();

				if(line != null) {
					System.out.println("(Server) Message incoming from the client: "+line);

					if(line.equalsIgnoreCase("whatTimeIsit")) {

						Calendar c = Calendar.getInstance();
						String date = c.getTime().toString();

						bwritter.write(date+"\n");
						bwritter.flush();
					} else if(line.equalsIgnoreCase("remoteIpConfig")) {

						String ipSend = InetAddress.getLocalHost().getHostAddress();					
						bwritter.write(ipSend+"\n");
						bwritter.flush();

					} else if(line.equalsIgnoreCase("interface")) {

						Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
						String interfacesInfo = "Interfaces: ";
						while (interfaces.hasMoreElements()) {
							NetworkInterface netN = interfaces.nextElement();
							if(netN.isUp()) {

								interfacesInfo += netN.getName()+" / ";				

							}
						}

						bwritter.write(interfacesInfo+"\n");
						bwritter.flush();	

					} else if(line.equalsIgnoreCase("RTT")) {

						//Despues de recibir la linea indicadora "RTT" luego leo la alfa proveniente del cliente
						String alfaLine = breader.readLine();				
						bwritter.write("(Server) Returning alfa from server: "+alfaLine+"\n");
						bwritter.flush();
					} else if(line.equalsIgnoreCase("speed")) {

						//Despues de recibir la linea indicadora "speed" luego leo la bravo proveniente del cliente
						String bravoLine = breader.readLine();				
						bwritter.write("(Server) Returning bravo from server: "+bravoLine+"\n");
						bwritter.flush();
					}

					else {
						bwritter.write("(Server) Unknown command"+"\n");
						bwritter.flush();
					}
				} 			

				socket.close();
				System.out.println("(Server) Server connection closed");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}