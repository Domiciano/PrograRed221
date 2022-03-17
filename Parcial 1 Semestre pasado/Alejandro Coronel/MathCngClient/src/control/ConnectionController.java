package control;

import com.TCPConnection;
import javafx.application.Platform;
import view.ConnectionWindow;
import view.ChallengeWindow;

public class ConnectionController implements TCPConnection.OnConnectionListener{	
	private ConnectionWindow view;
	private TCPConnection connection;
	
	public ConnectionController(ConnectionWindow view) {
		this.view = view;
		init();
	}
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setConnectionListener(this);	
		view.getBtnConnect().setOnAction(			
				e -> {
					String ip = view.getIpTF().getText();
					String puerto = view.getPortTF().getText();
					int puertoInt = Integer.parseInt(puerto);
					connection.setIp(ip);
					connection.setPuerto(puertoInt);
					connection.start();
				}			
		);	
	}

	@Override
	public void onConnection() {
		//Estamos conectados
		//No se puede usar metodos con resultado grafico en un hilo que no sea el principal
		Platform.runLater(
				
				()->{
					ChallengeWindow window = new ChallengeWindow();
					window.show();
					view.close();
				}
				
				);
	}
	
}
