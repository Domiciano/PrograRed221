package main;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.view.ConnectionView;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ConnectionView view = new ConnectionView();
		view.show();
	}

}
