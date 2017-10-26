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
	private static boolean custom = false;
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

	/**
	 * The following method gets the overall score by the user and sets the result text-field to their 
	 * answer and either enables or disables their button depending on how well they did.
	 * @return
	 */
	public Label getScoreLabel() {
		ScoreModel scores = new ScoreModel();
		if(!ResultController.custom) {
		scores.addNewScore( recordController.getScore());
		} else {
			ResultController.setCustom(false);
		}
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

	/**
	 * The following method saves the score and username of the current user into a text file 
	 * for the next use.
	 */
	public void saveScore() {
		
		if(!custom) {
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
		else {
			custom = false;
		}
	}

	/**
	 * The following method reads the txt file and checks if the user has achieved a new personal or the high
	 * score and creates a notification to show the user.
	 */
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
				//If the user got the high score the notification informs the user of so
				Notifications hard = Notifications.create().title("High score!!").text("Previous highscore was:" + highscore ).graphic(null).hideAfter(Duration.seconds(2))
						.position(Pos.TOP_LEFT);
				hard.showConfirm();
			}
			else if(recordController.getScore() > 7 && max < 7) {
				//If the user got a new personal best and got over 7 it informs the user he unlocked hard mode
				Notifications hard = Notifications.create().title("New Personal Best!").text("Your previous best was:" + max + " \\n You have unlocked hard mode!").graphic(null).hideAfter(Duration.seconds(2))
						.position(Pos.TOP_LEFT);
				hard.showConfirm();
			}
			else {
				//If the user got a new personal best it informs so
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

	public static boolean isCustom() {
		return custom;
	}

	public static void setCustom(boolean custom) {
		ResultController.custom = custom;
	}


}
