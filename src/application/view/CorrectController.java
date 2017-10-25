package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.SpeechScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class CorrectController extends AbstractController implements Initializable{

	private static int maxQ;
	private RecordController rC = new RecordController();
	private Alert alert = new Alert(AlertType.WARNING);
	@FXML
	private Button Continue;
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		//If at any moment the quit button is pressed the scores and questions are reset;
		alert.setTitle("Warning");
		alert.setHeaderText("Quit?");
		alert.setContentText("You will lose all progress if you quit");
		ButtonType buttonYes = new ButtonType("Ok");
		ButtonType buttonNo = new ButtonType("No");
		alert.getButtonTypes().setAll(buttonYes,buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonYes) {
			rC.resetQno();
			rC.resetScore();
			changeScene(event,"Menu");
		} else {
			alert.close();
		}
	}
	
	public void setMaxQ(int i) {
		maxQ = i;
	}

	/**
	 * Once the continue button has been pressed the scene changed to the next question
	 * @param event
	 * @throws IOException
	 */
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(PraticeRecordController.praticeMode) {
			// if pratice mode continue button leads back to the pratice module
			PraticeRecordController.praticeMode = false;
			changeScene(event,"PraticeModule");
		}
		else {
			if(rC.getQno() == maxQ) {
				//If its the last question scene moves to the Result scene
				changeScene(event,"Result");
				rC.resetQno();
				rC.resetScore();
			}
			else {
				changeScene(event,"Record");
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(PraticeRecordController.praticeMode) {
			Continue.setText("Return to Pratice Module");
		}
		
	}

}
