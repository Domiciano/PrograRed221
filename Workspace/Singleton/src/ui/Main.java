package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("VentanaA.fxml"));
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		
		FXMLLoader loaderA = new FXMLLoader(Main.class.getResource("VentanaB.fxml"));
		Parent pA = (Parent) loaderA.load();
		
		Scene sceneA = new Scene(pA);
		Stage stageA = new Stage();
		stageA.setScene(sceneA);
		stageA.show();
	}

}
