package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.Equation;
import application.model.ListenerWorker;
import application.model.NumberGenerator;
import application.model.ScoreModel;
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
import javafx.scene.text.Font;

public class PraticeRecordController extends AbstractController implements Initializable{

	private enum Status{
		NEW_QUESTION, NO_ANS, WRONG_ANS
	}

	private enum QType{
		QUESTION, EQUATION
	}

	ListenerWorker worker = new ListenerWorker();
	private static String userAns;
	private  ArrayList<String> words = new ArrayList<String>();

	private SpeechScript speech;

	private static Status status = Status.NEW_QUESTION;
	private static QType qType;

	//pop-up when user doesnt say anything
	private Alert alert = new Alert(AlertType.WARNING);

	@FXML
	private ProgressBar pb;
	@FXML
	private Label numLabel;
	@FXML
	private Label questionLabel;
	@FXML
	private Button listenButton = new Button();
	@FXML
	private Button submitButton = new Button();
	public static boolean praticeMode = false;
	private NumberGenerator A = new NumberGenerator();
	private PraticeModuleController B = new PraticeModuleController();

	private void setStatus(Status s){
		status = s;
	}

	public void setNumLabel(){
		numLabel.setText(""+B.getpraticeNumber());
		speech = new SpeechScript(this);	
		listenButton.setDisable(true);
		submitButton.setDisable(true);
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
	}

	// when submit button is pressed
	public void submit(ActionEvent event) throws IOException{
		speech.readAnswerFile();
		words = speech.getWords();
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
				changeScene(event,"Record");
			} else {
				alert.close();
			}
		} else {
			/* 
			 * if words is not empty
			 */		
			userAns = String.join(" ", words);			
			String maoriWord = "";
			maoriWord = A.getMaoriNum(B.getpraticeNumber());
			// user is correct
			if(userAns.equalsIgnoreCase(maoriWord)) {
				setStatus(Status.NEW_QUESTION);
				this.praticeMode = true;
				changeScene(event, "Correct");
			} else {
				this.praticeMode = true;
				changeScene(event, "Wrong");
			}
		}

	}

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		this.praticeMode = false;
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
