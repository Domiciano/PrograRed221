package control;

import java.util.UUID;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import model.User;
import view.GameWindow;
import view.ConnectionWindow;

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
					try {
						String ip = view.getIpTF().getText();
						String puerto = view.getPortTF().getText();
						int puertoInt = Integer.parseInt(puerto);
						connection.setIp(ip);
						connection.setPuerto(puertoInt);
						connection.start();
						Gson gson = new Gson();
						User user = new User(UUID.randomUUID().toString(), view.getUsernameTF().getText());
						String json = gson.toJson(user);
						connection.getEmisor().sendMessage(json);
					} catch (NullPointerException ex) {
						// e.printStackTrace();
						System.err.println("Server is closed");
					}
				}

		);

	}

	@Override
	public void onConnection() {
		Platform.runLater(() -> {
			GameWindow window = new GameWindow();
			window.show();
			view.close();
		});
	}

}
