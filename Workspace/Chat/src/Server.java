import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	static BufferedWriter writer;
	
	public static void main(String[] args) {
		
		new Thread(
				()->{
					try {
						ServerSocket server = new ServerSocket(6000);
						Socket socket = server.accept();
						
						
						writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(true) {
							String line = reader.readLine();
							System.out.println("Recibido: "+line);
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		).start();
		
		Scanner scanner = new Scanner(System.in);
			
		while(true) {
			String line = scanner.nextLine();
			if(writer != null) {
				new Thread(()->{
					try {
						writer.write(line+"\n");
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}).start();
			}
		}
		
		
	}

}
