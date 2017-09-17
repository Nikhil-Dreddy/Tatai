package application.view;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable{
	NumberGenerator number = new NumberGenerator();
	@FXML
	Label a;
	
	public void easyButtonClicked(ActionEvent event) throws IOException{
		number.setDifficultyToEasy();
		changeSceneToRecord(event);
	}
	

	public void hardButtonClicked(ActionEvent event) throws IOException{
		number.setDifficultyToHard();
		changeSceneToRecord(event);
	}
	
	// Change scene to record scene when button is pushed
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		Parent recordViewParent;
		
		recordViewParent = FXMLLoader.load(getClass().getResource("Pass.fxml"));
		Scene recordViewScene = new Scene(recordViewParent);
		
//		Get the stage
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		stage.setScene(recordViewScene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
