package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.ListenerWorker;
import application.model.NumberGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class WrongAgainController extends AbstractController implements Initializable{
	
	private ListenerWorker worker = new ListenerWorker();
	@FXML
	private Label userAnsLabel;
	private RecordController rC = new RecordController();
	@FXML
	private Label correctAnsLabel;
	
	private static int maxQ;
	
	private static String correctAns;

	public void setMaxQ(int i) {
		maxQ = i;
	}
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		rC.resetQno();
		rC.resetScore();
		changeScene(event,"Menu");
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
		correctAnsLabel.setText(correctAns);
	}
}
