package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import application.model.SpeechScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RecordController implements Initializable{
	
	NumberGenerator numberGenerator = new NumberGenerator();
	
	private static int questionNo = 0;
	
	private static int score;
	private static int mistakes;
	private SpeechScript speech;
	
	@FXML
	private Label numLabel;
	@FXML
	private Label questionLabel;
	
	public void setNumLabel(){
		numberGenerator.generateNum();
		numLabel.setText(String.valueOf(numberGenerator.getNum()));
		questionNo++;
		speech = new SpeechScript(numberGenerator.getNum());
		if(questionNo == 10 ){
			questionNo = 0;
			
		}
		questionLabel.setText(questionNo +"/10");	
		
	}
	
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		Parent menuViewParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene menuViewScene = new Scene(menuViewParent);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(menuViewScene);
		stage.show();
	}
	
	public void Record(ActionEvent event) throws IOException{
		
		new Thread(speech).start();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}

}
