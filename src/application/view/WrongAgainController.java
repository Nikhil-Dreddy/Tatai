package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.ListenerWorker;
import application.model.NumberGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class WrongAgainController extends AbstractController implements Initializable{
	
	private ListenerWorker worker = new ListenerWorker();
	@FXML
	private Label userAnsLabel;
	private RecordController rC = new RecordController();
	@FXML
	private Label correctAnsLabel;
	private Alert alert = new Alert(AlertType.WARNING);
	private static int maxQ;
	
	private static String correctAns;

	public void setMaxQ(int i) {
		maxQ = i;
	}
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		//If at any moment the quit button is pressed the scores and questions are reset;
		alert.setTitle("Warning");
		alert.setHeaderText("Quit?");
		alert.setContentText("You will lose all progress if you quit");
		ButtonType buttonYes = new ButtonType("Ok");
		ButtonType buttonNo = new ButtonType("No");
		alert.getButtonTypes().setAll(buttonYes,buttonNo);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonYes) {
			rC.resetQno();
			rC.resetScore();
			changeScene(event,"Menu");
		} else {
			alert.close();
		}
	}
	
	// for the next question botton
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(rC.getQno() == maxQ) {
			changeScene(event,"Result");
			rC.resetQno();
			rC.resetScore();
		}
		else {
		changeScene(event,"Record");
		}
	}
	
	public void listenRecording(ActionEvent event) throws IOException{
		new Thread(worker).start();
	} 
	
	public void setCorrectAns(String ans) {
		correctAns = ans;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		RecordController recordController = new RecordController();
		userAnsLabel.setText(recordController.getWrongAns());
		NumberGenerator numberGenerator = new NumberGenerator();
//		correctAnsLabel.setText(numberGenerator.getMaoriNum(numberGenerator.getNum()));
		correctAnsLabel.setText(correctAns);
	}
}
