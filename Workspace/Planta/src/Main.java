import java.io.*;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Main {

	public static void main(String[] args) {
		
		
		new Thread(()->{
			while(true) {
				try {
					Thread.sleep(1000);
					HttpsURLConnection connection = (HttpsURLConnection)(new URL("https://facelogprueba.firebaseio.com/sensors.json").openConnection());
					connection.setRequestMethod("PUT");
		            connection.setRequestProperty("Content-Type", "application/json-patch+json");
		            connection.setRequestProperty("accept", "application/json");
		            connection.setDoInput(true);
		            connection.setDoOutput(true);

		            OutputStream os = connection.getOutputStream();
		            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

		            System.out.println("Enviando nuevos valores");
		            int A=(int)(220*Math.random());
		            int B=(int)(220*Math.random());
		            int C=(int)(220*Math.random());
		            int D=(int)(220*Math.random());
		            writer.write("{\"sensor1\":{\"voltage\":"+A+"},\"sensor2\":{\"voltage\":"+B+"},\"sensor3\":{\"voltage\":"+C+"},\"sensor4\":{\"voltage\":"+D+"}}");
		            writer.flush();
		            
		            InputStream is = connection.getInputStream();
		            ByteArrayOutputStream baos = new ByteArrayOutputStream();

		            byte[] buffer = new byte[4096];
		            int bytesRead;
		            while ((bytesRead = is.read(buffer)) != -1) {
		                baos.write(buffer, 0, bytesRead);
		            }
		            is.close();
		            baos.close();
		            os.close();
		            connection.disconnect();
		            String out = new String(baos.toByteArray(), "UTF-8");
		            System.out.println(out);
		            
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void POSTRamdonData() {
		
	}

}
