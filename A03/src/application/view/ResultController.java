package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultController extends AbstractController implements Initializable{

	@FXML
	private Label scoreLabel ;

	@FXML
	private Button nextLevelButton = new Button();

	private NumberGenerator number = new NumberGenerator();

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	public void changeSceneToHard(ActionEvent event) throws IOException {
		number.setDifficultyToHard();
		changeScene(event,"Record");
	}
	public Label getScoreLabel() {
		scoreLabel.setText(RecordController.score + "/10");
		if(RecordController.score > 7) {
			RecordController.score = 0;
			RecordController.questionNo = 0;
			nextLevelButton.setDisable(false);
		}
		else {
			RecordController.score = 0;
			RecordController.questionNo = 0;
			nextLevelButton.setDisable(true);
		}
		
		return scoreLabel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getScoreLabel();
	}


}
