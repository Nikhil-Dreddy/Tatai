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
	
	private int score;

	@FXML
	private Label scoreLabel ;

	@FXML
	private Button nextLevelButton = new Button();

	private NumberGenerator number = new NumberGenerator();
	
	private RecordController recordController = new RecordController();

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	public void changeSceneToHard(ActionEvent event) throws IOException {
		number.setDifficultyToHard();
		changeScene(event,"Record");
	}
	
	public Label getScoreLabel() {
		score = recordController.getScore();
		scoreLabel.setText(score + "/9");
		if(score > 6) {
			nextLevelButton.setDisable(false);
		}
		else {
			nextLevelButton.setDisable(true);
		}
		
		return scoreLabel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getScoreLabel();
	}


}
