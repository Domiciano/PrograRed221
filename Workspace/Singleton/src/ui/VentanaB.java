package ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Person;
import model.PersonReg;

public class VentanaB implements Initializable, PersonReg.OnDataChangedListener{

    @FXML
    private TextArea outputTA;

    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PersonReg.getInstance().setListener(this);
	}


	@Override
	public void onDataChanged() {
		outputTA.setText("");
		for(Person p : PersonReg.getInstance().getData()) {
			outputTA.appendText(p.getNombre()+":"+p.getNatID()+"\n\n");
		}
	}
    
    
    

}

