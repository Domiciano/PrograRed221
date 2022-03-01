package main;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
	}

	// Atributos de objeto

	// Metodos de objeto
	public Main() {

		// Generar el hilo long polling

		new Thread(() -> {

			try {
				while (true) {
					URL url = new URL("https://facelogprueba.firebaseio.com/sensors.json");
					HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
					InputStream is = con.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					int readBytes = 0;
					byte[] buffer = new byte[1024];
					while ((readBytes = is.read(buffer)) != -1) {
						baos.write(buffer, 0, readBytes);
					}
					is.close();
					baos.close();

					String datos = new String(baos.toByteArray(), "UTF-8");
					System.out.println(datos);
					
					Thread.sleep(1500);
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}).start();

	}

}
