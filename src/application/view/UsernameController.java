package application.view;

import java.io.IOException;
import java.util.Optional;

import application.model.ScoreModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UsernameController extends AbstractController {
	@FXML
	TextField usernameField;
	Alert alert = new Alert(AlertType.ERROR);
	// Change scene to record scene when button is pushed
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(!usernameField.getText().contains(" ")) {
			ScoreModel.username = usernameField.getText();
			changeScene(event,"Menu");
		}
		else {
			alert.setTitle("Error");
			alert.setHeaderText("Wrong username format");
			alert.setContentText("Username has whitespaces");
			ButtonType buttonYes = new ButtonType("Ok");
			alert.getButtonTypes().setAll(buttonYes);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == buttonYes) {
				changeScene(event,"Username_Scene");
			} else {
				alert.close();
			}
		}
	}

	//Change scene to record scene when button is pushed
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		System.exit(0);
	}

}
