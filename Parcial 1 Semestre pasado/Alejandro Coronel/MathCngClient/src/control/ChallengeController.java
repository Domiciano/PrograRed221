package control;
import com.google.gson.Gson;
import com.Receptor.OnMessageListener;

import java.io.File;

import com.TCPConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import model.Answer;
import model.Disconnect;
import model.Generic;
import model.Question;
import model.Refresh;
import model.Win;
import view.ChallengeWindow;

public class ChallengeController implements OnMessageListener {

	private ChallengeWindow view;
	private TCPConnection connection;

	public ChallengeController(ChallengeWindow view) {
        
		this.view = view;

		init();

	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);
		view.getAnswerBtn().setOnAction(
				event ->{
					Gson gson = new Gson();
					double answer = Double.parseDouble(view.getAnswerTF().getText());
					char c = view.getQuestionlabel().getText().charAt(0);
					Answer a = new Answer(Character.getNumericValue(c), answer);
					String json = gson.toJson(a);
					TCPConnection.getInstance().getEmisor().sendMessage(json);	
					view.getAnswerTF().clear();
					
				});
		
		     view.setOnCloseRequest(new EventHandler<WindowEvent>() {
			 @Override
			 public void handle(WindowEvent we) {
				 Gson gson = new Gson();
				 Disconnect d = new Disconnect(false);
				 String json = gson.toJson(d);
				 TCPConnection.getInstance().getEmisor().sendMessage(json);
			}
		});   

	}

	@Override
	public void onMessage(String msg) {
		Platform.runLater( // run on UI Thread
				() -> {

					Gson gson = new Gson();
					Generic msjObj = gson.fromJson(msg, Generic.class);
					switch(msjObj.getType()) {

					case "Rejected":
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Maximo de jugadores alcanzado");
						alert.setContentText("En este momento ya existen 2 jugadores en una partida \nPor favor vuelva a conectarse en otro momento");
						alert.show();
						disconnectfromServer();
						Disconnect d = new Disconnect(true);
						String json = gson.toJson(d);
						TCPConnection.getInstance().getEmisor().sendMessage(json);			
						break;

					case "Refresh":	
						Refresh r = gson.fromJson(msg, Refresh.class);
					     view.getOpponentlabel().setText(r.getQnumber()+"/5");			
						break;
					case "Wrong":
						
						Alert alertw = new Alert(Alert.AlertType.ERROR);
						alertw.setHeaderText("Repuesta Incorrecta");
						alertw.setContentText("Por favor vuelva a intentarlo");
						alertw.show();				
						break;
					case "Question":
						Question q = gson.fromJson(msg, Question.class);
						if(q.getId() == 1) {	
							view.getQuestionlabel().setText("1/5");
							view.getOpponentlabel().setText("1/5");
							view.getQuestion().setText(q.getStatement());
							
						}else {
							view.getQuestionlabel().setText((q.getId())+"/5");
							view.getQuestion().setText(q.getStatement());
						}
						break;
					case "Win":	
						Image image;
						ImageView imageView;
						File f;
						Win w = gson.fromJson(msg, Win.class);
						if(w.isWinner() == true) {					
							view.getQuestion().setText("GANASTE");
							f = new File("extra\\necogif.gif");
							image = new Image(f.toURI().toString());
							imageView = new ImageView(image);
							imageView.setFitHeight(100);
							imageView.setFitWidth(100);
							view.getQuestion().setGraphic(imageView);						
						}else {
							view.getQuestion().setText("PERDISTE");	
							f = new File("extra\\necoperdiste.png");
							image = new Image(f.toURI().toString());
							imageView = new ImageView(image);
							imageView.setFitHeight(100);
							imageView.setFitWidth(100);
							view.getQuestion().setGraphic(imageView);	
						}
						break;				
					}					
				}	
				);
	}


	public void disconnectfromServer() {
		Platform.runLater( // run on UI Thread
				() -> {
					view.close();
				}

				);

	}

}
