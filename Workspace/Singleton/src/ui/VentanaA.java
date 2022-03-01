package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;
import model.PersonReg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VentanaA {

    @FXML
    private TextField nameTF;

    @FXML
    private TextField natIDTF;

    @FXML
    private Button submitBtn;

    @FXML
    void submit(ActionEvent event) throws Exception {
    	Person person = new Person(natIDTF.getText(), nameTF.getText());
    	PersonReg.getInstance().addPerson(person);
    	
    	
    }

}


