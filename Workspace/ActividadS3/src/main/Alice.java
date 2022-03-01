package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Alice {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(6000);
		System.out.println("Esperando...");
		Socket socket = server.accept();
		
		System.out.println("Recibiendo...");
		
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(
				new File("/Users/domicianorincon/Documents/Recibido/libro.pdf")
		);
		
		int readBytes = 0;
		byte[] buffer = new byte[1024];		
		while(  (readBytes = is.read(buffer))  != -1 ) {
			fos.write(buffer, 0, readBytes);
		}
		is.close();
		fos.close();
	}

}
