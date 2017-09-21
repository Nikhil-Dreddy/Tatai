package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RecordController extends AbstractController implements Initializable{
	
	NumberGenerator numberGenerator = new NumberGenerator();
	
	private static int questionNo = 0;
	private static int score = 0;
//	private static int mistakes;
	private static String ans;
	
	// if true, then user is on his last try before getting question wrong
	private static boolean tryAgain = false; 
	
	@FXML
	private Label numLabel;
	@FXML
	private Label questionLabel;
	@FXML
	private TextField ansTextField;
	
	public void setNumLabel(){
		// if 10 questions have been asked, send to *** scene
		if (questionNo == 10){
			
		}
		// if user is not on last try, then get random number
		// or else just use number that they got wrong so they can try again
		if (! tryAgain){
			numberGenerator.generateNum();
		}
		numLabel.setText(String.valueOf(numberGenerator.getNum()));
		
		questionLabel.setText(score+"/"+questionNo);	
	}
	
	// this button gets answer and saves it
	// pretty much same functionality as real record
	public void record(ActionEvent event) throws IOException{
		ans = ansTextField.getText();
		if (numberGenerator.isCorrect(ans)){
			// if user is correct send to correct screen
			// and increase their score
			changeScene(event, "Correct");
			score++;
			questionNo++;
			tryAgain = false;
		} else {
			// user is wrong
			if (! tryAgain){
				// if first time wrong, let them try again
				tryAgain = true;
				changeScene(event, "Wrong");
			} else {
				// user is too retarded and qusetion is wrong
				// skip to next question
				questionNo++;
				tryAgain = false;
				changeScene(event, "WrongAgain");
			}
		}
	}
	
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	public String getWrongAns(){
		return ans;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}

}
