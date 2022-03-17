package control;

import comm.TCPConnection;
import javafx.application.Platform;
import view.ConnectionWindow;
import view.GameWindow;

public class ConnectionController implements TCPConnection.OnConnectionListener {

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
				});

	}

	@Override
	public void onConnection() {
		Platform.runLater(
				() -> {
					GameWindow window = new GameWindow();
					window.show();
					view.close();
				}

		);
	}

}
