package application.view;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import javafx.fxml.Initializable;

public class MenuController extends AbstractController { 
	NumberGenerator number = new NumberGenerator();
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
		changeScene(event,"Record");
	}


}
