package application.view;

import java.io.IOException;
import java.util.Optional;

import application.model.SpeechScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class PraticeModuleController extends AbstractController {
	@FXML
	TextField Number;

	Alert alert = new Alert(AlertType.ERROR);
	private static int praticeNumber = -1;
	private boolean numbererror = false;
	
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		
		changeScene(event,"Menu");
	}

	//The following method changes to practice record scene 
	public void changeSceneToPraticeRecord(ActionEvent event) throws IOException{
		
		try {
			//it checks if the user has entered an int if hasn't the relevant alert pops-up
			this.praticeNumber = Integer.parseInt(Number.getText());
		}
		catch(NumberFormatException a) {
			alert.setTitle("Error");
			alert.setHeaderText("Wrong number");
			alert.setContentText("Text entered instead of number");
			ButtonType buttonYes = new ButtonType("Ok");
			alert.getButtonTypes().setAll(buttonYes);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == buttonYes) {
				changeScene(event,"PraticeModule");
				this.numbererror = true;
			} else {
				alert.close();
			}
		}
		//if the number entered is less then 1 or greator then 99 the relevant pop-up is displayed
		if((this.praticeNumber < 1 | this.praticeNumber > 99) & !this.numbererror) {
			alert.setTitle("Error");
			alert.setHeaderText("Wrong number");
			alert.setContentText("Number is not within the correct range of 1-99");
			ButtonType buttonYes = new ButtonType("Ok");
			alert.getButtonTypes().setAll(buttonYes);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == buttonYes) {
				changeScene(event,"PraticeModule");
			} else {
				alert.close();
			}
		}
		else if(!this.numbererror){
			changeScene(event,"PraticeRecord");
		}
	}
	
	public int getpraticeNumber() {
		return this.praticeNumber;
	}

}
