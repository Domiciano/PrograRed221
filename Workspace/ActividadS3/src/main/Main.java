package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Descargando...");
		String site = "https://github.com/Domiciano/PrograRed221/raw/master/javanet.pdf";
		URL url = new URL(site);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		InputStream is = connection.getInputStream();
		
		
		Socket socket = new Socket("127.0.0.1", 6000);
		
		OutputStream os = socket.getOutputStream();
		
		
		System.out.println("Enviando...");
		int readBytes = 0;
		byte[] buffer = new byte[1024];		
		while(  (readBytes = is.read(buffer))  != -1 ) {
			os.write(buffer, 0, readBytes);
		}
		is.close();
		os.close();
		
		
		
		
	}

}
