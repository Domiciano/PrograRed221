package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket  = new Socket("4.tcp.ngrok.io", 12228);
			System.out.println("Conectado");
			
			//Telefono roto
			
			
			//Intercambio de datos
			InputStream is = socket.getInputStream();
			
			OutputStream os = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			
			
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String line = scanner.nextLine();
				bw.write(line+"\n");
				bw.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
