package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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

public class RecordController extends AbstractController implements Initializable{

	private enum Status{
		NEW_QUESTION, NO_ANS, WRONG_ANS
	}

	private enum QType{
		QUESTION, EQUATION, CUSTOM
	}
	@FXML 
	private Button record = new Button();
	ListenerWorker worker = new ListenerWorker(this);
	NumberGenerator numberGenerator = new NumberGenerator();
	Equation equation = new Equation();

	private static int questionNo = 0;
	private static int score = 0;
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

	private void setStatus(Status s){
		status = s;
	}

	// Displays the number/equations for the user to analyze
	public void setNumLabel(){
		if (qType == QType.QUESTION) {
			// if user is not on last try, then get random number
			// or else just use number that they got wrong so they can try again
			if (status == Status.NEW_QUESTION){
				numberGenerator.generateNum();
			}
			numLabel.setText(String.valueOf(numberGenerator.getNum()));
		} else if (qType == QType.EQUATION) {
			if (status == Status.NEW_QUESTION){
				// generate new equation
				equation.generateEquation();
			}
			numLabel.setText(equation.getEquation());
			numLabel.setFont(new Font(30));
		} else if (qType == QType.CUSTOM) {
			CustomEquationsController custEqCont = new CustomEquationsController();
			numLabel.setText(custEqCont.getEquation());
			numLabel.setFont(new Font(100));
		}
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
		this.makeButtonsDisible();
		speech = new SpeechScript(this);
		speech.setEvent(event);
		new Thread(speech).start();	
		updatePB();
	}

	public void makeButtonsDisible(){
		listenButton.setDisable(true);
		submitButton.setDisable(true);
		record.setDisable(true);
	}
	public void makeButtonsVisible(){
		listenButton.setDisable(false);
		submitButton.setDisable(false);
		record.setDisable(false);
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
			if (qType == QType.QUESTION) {
				maoriWord = numberGenerator.getMaoriNum(numberGenerator.getNum());
			} else if (qType == QType.EQUATION) {
				maoriWord = numberGenerator.getMaoriNum(equation.getAns());
			} else if (qType == QType.CUSTOM) {
				Object result = null;
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				CustomEquationsController custEqCont = new CustomEquationsController();
				try {
					maoriWord = numberGenerator.getMaoriNum((Integer) engine.eval(custEqCont.getEquation().replace("x", "*")));
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}

			// user is correct
			if(userAns.equalsIgnoreCase(maoriWord)) {
				score++;
				questionNo++;
				setStatus(Status.NEW_QUESTION);
				changeScene(event, "Correct");
			} else {
				// user is wrong
				if (status != Status.WRONG_ANS){
					// if first time wrong, let them try again
					setStatus(Status.WRONG_ANS);						
					changeScene(event, "Wrong");
				} else if(status == Status.WRONG_ANS) {
					// user gets question wrong again
					// skip to next question
					questionNo++;
					setStatus(Status.NEW_QUESTION);	
					WrongAgainController wrongAgainController = new WrongAgainController();
					wrongAgainController.setCorrectAns(maoriWord);
					changeScene(event, "WrongAgain");
				}
			}
		}

	}

	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		alert.setTitle("Warning");
		alert.setHeaderText("Quit?");
		alert.setContentText("You will lose all progress if you quit");
		ButtonType buttonYes = new ButtonType("Ok");
		ButtonType buttonNo = new ButtonType("No");
		alert.getButtonTypes().setAll(buttonYes,buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonYes) {
			this.resetQno();
			this.resetScore();
			setStatus(Status.NEW_QUESTION);
			changeScene(event,"Menu");
		} else {
			alert.close();
		}
	}

	public String getWrongAns(){
		return userAns;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNumLabel();
	}

	// Makes the progress bar run for 2 seconds white the user records their voice
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
		this.makeButtonsDisible();
		updatePB();
		worker = new ListenerWorker(this);
		new Thread(worker).start();
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

	public void setQTypeQuestion() {
		qType = QType.QUESTION;
	}

	public void setQTypeEquation() {
		qType = QType.EQUATION;
	}

	public void setQTypeCustom() {
		qType = QType.CUSTOM;
	}
}
