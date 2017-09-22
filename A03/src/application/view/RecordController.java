package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import application.model.SpeechScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RecordController extends AbstractController implements Initializable{
	
	NumberGenerator numberGenerator = new NumberGenerator();
	
	public static int questionNo = 0;
	public static int score = 0;
//	private static int mistakes;
	private static String userAns;
	
	private SpeechScript speech;
	
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
		if (!tryAgain){
			numberGenerator.generateNum();
		}
		numLabel.setText(String.valueOf(numberGenerator.getNum()));
		speech = new SpeechScript();
		questionLabel.setText(score+"/"+questionNo);	
	}
	
	// this button gets answer and saves it
	// pretty much same functionality as real record
	public void record(ActionEvent event) throws IOException{
		speech.setEvent(event);
		new Thread(speech).start();	
	}
	
	
	public void afterResult(ArrayList<String> words, ActionEvent event ) {
		
		if(words == null){
			userAns = "user said nothign";
		} else {
			userAns = String.join(" ", words);
		}
		
		String maoriWord = numberGenerator.getMaoriNum();
		
		if(userAns.equalsIgnoreCase(maoriWord)) {
			try {
				changeScene(event, "Correct");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			score++;
			questionNo++;
			tryAgain = false;
		} else {
			// user is wrong
			if (! tryAgain){
				// if first time wrong, let them try again
				tryAgain = true;
				try {
					changeScene(event, "Wrong");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// user is too retarded and qusetion is wrong
				// skip to next question
				questionNo++;
				tryAgain = false;
				try {
					changeScene(event, "WrongAgain");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Result");
	}
	
	public String getWrongAns(){
		return userAns;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}

}
