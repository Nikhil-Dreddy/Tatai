package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.ListenerWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class WrongController extends AbstractController implements Initializable{

	RecordController recordController = new RecordController();
	ListenerWorker worker = new ListenerWorker();
	@FXML
	private Label wrongAns;
	@FXML
	private Button Continue;
	private Alert alert = new Alert(AlertType.WARNING);
	PraticeRecordController pratice = new PraticeRecordController();
	
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
					recordController.resetQno();
					recordController.resetScore();
					changeScene(event,"Menu");
				} else {
					alert.close();
				}
	}

	// for the try again button
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(PraticeRecordController.praticeMode) {
			PraticeRecordController.praticeMode = false;
			changeScene(event,"PraticeModule");
		}
		else {
			changeScene(event,"Record");
		}
	}

	public void listenRecording(ActionEvent event) throws IOException{
		new Thread(worker).start();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(PraticeRecordController.praticeMode) {
			Continue.setText("Return to Pratice Module");
			wrongAns.setText(pratice.getWrongAns());
		}
		else {
			wrongAns.setText(recordController.getWrongAns());
		}
	}
}