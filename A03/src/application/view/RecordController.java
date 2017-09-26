package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.NumberGenerator;
import application.model.SpeechScript;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class RecordController extends AbstractController implements Initializable{
	
	private enum Status{
		NEW_QUESTION, NO_ANS, WRONG_ANS
	}

	NumberGenerator numberGenerator = new NumberGenerator();

	public static int questionNo = 8;
	public static int score = 7;
	private static String userAns;

	private SpeechScript speech;

	private ResultController rController = new ResultController();

//	private static boolean tryAgain = false; 
	private static Status status = Status.NEW_QUESTION;
		
	//pop-up when user doesnt say anything
	private Alert alert = new Alert(AlertType.WARNING);
	
	@FXML
	private ProgressBar pb;
	@FXML
	private Label numLabel;
	@FXML
	private Label questionLabel;
	@FXML
	private TextField ansTextField;
	
	private void setStatus(Status s){
		status = s;
	}

	public void setNumLabel(){
		
		// if user is not on last try, then get random number
		// or else just use number that they got wrong so they can try again
		if (status == Status.NEW_QUESTION){
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
		updatePB();
	}


	public void afterResult(ArrayList<String> words, ActionEvent event ) {
		if(questionNo >= 10) {
			try {
				changeScene(event,"Result");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			if(words.isEmpty()){
				setStatus(Status.NO_ANS);
				userAns = "";
				//If nothing was said a pop-up comes tell the user to re-record
				alert.setTitle("Error");
				alert.setHeaderText("No Word?");
				alert.setContentText("No word was said,press Ok to Re-record");
				ButtonType buttonYes = new ButtonType("Ok");
				ButtonType buttonQuit = new ButtonType("Quit");
				alert.getButtonTypes().setAll(buttonYes,buttonQuit);
				speech = new SpeechScript();
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == buttonYes) {
					try {
						changeScene(event,"Record");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if(result.get() == buttonQuit) {
					try {
						changeScene(event,"Menu");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				/* 
				 * if words is not empty
				 */		
				userAns = String.join(" ", words);			
				String maoriWord = numberGenerator.getMaoriNum();

				// user is correct
				if(userAns.equalsIgnoreCase(maoriWord)) {
					try {
						changeScene(event, "Correct");
					} catch (IOException e) {
						e.printStackTrace();
					}
					score++;
					questionNo++;
					setStatus(Status.NEW_QUESTION);
				} else {
					// user is wrong
					if (status != Status.WRONG_ANS){
						// if first time wrong, let them try again
						setStatus(Status.WRONG_ANS);
						try {
							changeScene(event, "Wrong");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if(status == Status.WRONG_ANS) {
						// user gets question wrong again
						// skip to next question
						questionNo++;
						setStatus(Status.NEW_QUESTION);
						try {
							changeScene(event, "WrongAgain");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}


	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	public String getWrongAns(){
		return userAns;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}
	
	public void updatePB(){
		new Thread(() -> {
			for (int i=0; i<=100; i++){
				final int position = i;
				Platform.runLater(new Runnable(){
	
					@Override
					public void run() {
						pb.setProgress(position/100.0);
					}
					
				});
				try{
					Thread.sleep(20);
				} catch (Exception e){
				}
			}
		}).start();
	}
	
}
