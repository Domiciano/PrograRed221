package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;



public class Main {

	public static void main(String[] args) {


		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			try {

				System.out.println("**BIENVENIDO**");
				System.out.println("Lista de Comandos disponibles");
				System.out.println("-remoteIpconfig");
				System.out.println("-interface");
				System.out.println("-whatTimeIsIt");
				System.out.println("-RTT");
				System.out.println("-speed");
				System.out.println("Por favor ingrese el comando que desee probar");
				System.out.println("Tenga en cuenta las mayusculas y minusculas");
				
				String line = scan.nextLine();

				System.out.println("Enviando solicitud de conexion...");
				Socket socket = new Socket("127.0.0.1",5000);
				System.out.println("conexion exitosa! :D");

				//Definicion lector y escritor
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bwritter = new BufferedWriter(osw);
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				switch(line){

				case "RTT":

					String msgRTT = new String(new byte[1024]);
					double inicioRTT = System.nanoTime();
					bwritter.write(line+"\n");
					bwritter.flush();
					bwritter.write(msgRTT+"\n");
					bwritter.flush();
					String msgSV = breader.readLine();
					System.out.println("Mensaje del servidor: "+msgSV);
					double tiempoTRTT = System.nanoTime() - inicioRTT;
					double tiempoRTTS = tiempoTRTT /1000000000;
					System.out.println("El tiempo de ida y venida es: "+tiempoRTTS+" segundos");
					break;

				case "speed":

					String msgSPD = new String(new byte[8192]);
					double inicioSPD = System.nanoTime();
					bwritter.write(line+"\n");
					bwritter.flush();
					bwritter.write(msgSPD+"\n");
					bwritter.flush();
					String msgSVSD = breader.readLine();
					System.out.println("Mensaje del servidor: "+msgSVSD);

					double tiempoSPD = System.nanoTime() - inicioSPD;
					double tiempoSPDS = tiempoSPD /1000000000;
					double velocidadTr = (msgSPD.getBytes().length / 1024) / tiempoSPDS;
					System.out.println("Velocidad de transmision: "+velocidadTr+"kbs/s");
					break;	

				default:
					
					bwritter.write(line+"\n");
					bwritter.flush();
					String command = breader.readLine();
					System.out.println(" Mensaje del servidor: "+command);
					break;
					
				}
 
				socket.close();
				System.out.println("Usted se ha desconectado del servidor");
				

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}
