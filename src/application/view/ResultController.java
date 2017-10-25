package application.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import application.model.NumberGenerator;
import application.model.ScoreModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ResultController extends AbstractController implements Initializable{

//	private int score;
	@FXML
	private Label scoreLabel ;

	@FXML
	private Button nextLevelButton = new Button();

	private NumberGenerator number = new NumberGenerator();

	private RecordController recordController = new RecordController();

	public static final String dir = System.getProperty("user.dir");

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	public void changeSceneToHard(ActionEvent event) throws IOException {
		number.setDifficultyToHard();
		changeScene(event,"Record");
	}

	public Label getScoreLabel() {
		ScoreModel scores = new ScoreModel();
		scores.addNewScore( recordController.getScore());
		
		int score = recordController.getScore();
		int questions = recordController.getQno();
		scoreLabel.setText(score + "/"+questions);
		if(score > 7) {
			nextLevelButton.setDisable(false);
		}
		else {
			nextLevelButton.setDisable(true);
		}

		return scoreLabel;
	}

	public void saveScore() {
		File scores;
		try {
			scores = new File("scores.txt");
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(scores, true));
			output.newLine();
			output.append(ScoreModel.username +" " +recordController.getScore());
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void motivation() {
		ScoreModel model = new ScoreModel();
		int max = 0;
		int highscore = 0;
		model.getData();
		for(int i = 0;i<model.getData().size();i++) {
			if(model.getData().get(i).getUsername().equals(ScoreModel.username)) {
				if(model.getData().get(i).getCorrect() > max) {
					max = model.getData().get(i).getCorrect();
				}
			}
			if(model.getData().get(i).getCorrect() > highscore) {
				highscore = model.getData().get(i).getCorrect();
			}
		}	

		if(recordController.getScore() > max) {
			if(recordController.getScore() > highscore) {
				Notifications hard = Notifications.create().title("High score!!").text("Previous highscore was:" + highscore ).graphic(null).hideAfter(Duration.seconds(2))
						.position(Pos.TOP_LEFT);
				hard.showConfirm();
			}
			else if(recordController.getScore() > 7 && max < 7) {
				Notifications hard = Notifications.create().title("New Personal Best!").text("Your previous best was:" + max + " \\n You have unlocked hard mode!").graphic(null).hideAfter(Duration.seconds(2))
						.position(Pos.TOP_LEFT);
				hard.showConfirm();
			}
			else {
				Notifications B = Notifications.create().title("New Personal Best!").text("Your previous best was:" + max).graphic(null).hideAfter(Duration.seconds(2))
						.position(Pos.TOP_LEFT);
				B.showConfirm();
			}
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		motivation();
		getScoreLabel();
		saveScore();
	}


}
