package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class VentanaA implements Initializable {

	@FXML
	private TextField messageTF;

	@FXML
	private Button sendBtn;

	@FXML
	private TextArea outputTA;

	@FXML
	void send(ActionEvent event) {
		String line = messageTF.getText();
		if (writer != null) {
			new Thread(() -> {
				try {
					writer.write(line + "\n");
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}
	}

	private BufferedWriter writer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new Thread(() -> {
			try {
				Socket socket = new Socket("127.0.0.1", 6000);

				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String line = reader.readLine();
					
					//Run on UI Thread
					Platform.runLater(()->{
						outputTA.appendText(line);
					});
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

}
