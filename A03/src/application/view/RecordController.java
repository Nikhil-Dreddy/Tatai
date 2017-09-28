package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.ListenerWorker;
import application.model.NumberGenerator;
import application.model.SpeechScript;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class RecordController extends AbstractController implements Initializable{
	
	private enum Status{
		NEW_QUESTION, NO_ANS, WRONG_ANS
	}
	
	ListenerWorker worker = new ListenerWorker();

	NumberGenerator numberGenerator = new NumberGenerator();

	private static int questionNo = 8;
	private static int score = 7;
	private static String userAns;
	private static ArrayList<String> words = new ArrayList<String>();

	private SpeechScript speech;

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
	@FXML
	private Button listenButton = new Button();
	@FXML
	private Button submitButton = new Button();
	
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
		speech = new SpeechScript(this);
		questionLabel.setText(score+"/"+questionNo);	
		listenButton.setDisable(true);
		submitButton.setDisable(true);
	}
	
	public int getScore(){
		return score;
	}

	// this button gets answer and saves it
	// pretty much same functionality as real record
	public void record(ActionEvent event) throws IOException{
		speech.setEvent(event);
		new Thread(speech).start();	
		updatePB();
	}
	
	
	public void makeButtonsVisible(){
		listenButton.setDisable(false);
		submitButton.setDisable(false);
		System.out.println("make buttons visble");
	}

	// when submit button is pressed
	public void submit(ActionEvent event) throws IOException{
		SpeechScript speechScript = new SpeechScript(this);
		words = speechScript.getWords();
		if(questionNo == 10) {
			score = 0;
			questionNo = 0;
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
				alert.getButtonTypes().setAll(buttonYes);
				speech = new SpeechScript(this);
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == buttonYes) {
					try {
						changeScene(event,"Record");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					alert.close();
				}
			} else {
				/* 
				 * if words is not empty
				 */		
				userAns = String.join(" ", words);			
				String maoriWord = numberGenerator.getMaoriNum();

				// user is correct
				if(userAns.equalsIgnoreCase(maoriWord)) {
					score++;
					questionNo++;
					setStatus(Status.NEW_QUESTION);
					
					try {
						changeScene(event, "Correct");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
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
		setStatus(Status.NEW_QUESTION);
	}

	public String getWrongAns(){
		return userAns;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}

		public int getQno() {
		return this.questionNo;
	}
	public void resetQno() {
		this.questionNo =0; 
	}
	public void resetScore() {
		this.score =0; 
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
	
	// for listen button
	public void listenRecording(ActionEvent event) throws IOException{
		new Thread(worker).start();
	}
	
}
