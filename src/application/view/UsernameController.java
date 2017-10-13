package application.view;

import java.io.IOException;

import application.model.ScoreModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UsernameController extends AbstractController {
	@FXML
	TextField usernameField;

	// Change scene to record scene when button is pushed
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		ScoreModel.username = usernameField.getText();
		changeScene(event,"Menu");
	}
	
	//Change scene to record scene when button is pushed
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		System.exit(0);
	}

}
