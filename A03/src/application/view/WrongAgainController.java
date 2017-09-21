package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class WrongAgainController extends AbstractController implements Initializable{
	
	
	@FXML
	private Label userAnsLabel;
	
	@FXML
	private Label correctAnsLabel;

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	// for the next question botton
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		changeScene(event,"Record");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		RecordController recordController = new RecordController();
		userAnsLabel.setText(recordController.getWrongAns());
		NumberGenerator numberGenerator = new NumberGenerator();
		correctAnsLabel.setText(numberGenerator.getMaoriNum());	
	}
}
