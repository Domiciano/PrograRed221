package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FinalWindow extends Stage{

	private Label resultLabel;
	private Label enemyUsernameLabel;
	private Scene scene;
	
	public FinalWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 340,169);
			this.setScene(scene);
			
			//Referencias
			enemyUsernameLabel = (Label) loader.getNamespace().get("enemyUsernameLabel");
			resultLabel = (Label) loader.getNamespace().get("resultLabel");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Label getResultLabel() {
		return resultLabel;
	}

	public void setResultLabel(Label resultLabel) {
		this.resultLabel = resultLabel;
	}

	public Label getEnemyUsernameLabel() {
		return enemyUsernameLabel;
	}

	public void setEnemyUsernameLabel(Label enemyUsernameLabel) {
		this.enemyUsernameLabel = enemyUsernameLabel;
	}
	
}
