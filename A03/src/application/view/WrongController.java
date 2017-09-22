package application.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.ListenerWorker;
import application.model.SpeechScript;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class WrongController extends AbstractController implements Initializable{

	RecordController recordController = new RecordController();
	ListenerWorker Worker;
	@FXML
	private Label wrongAns;


	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	// for the try again button
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		changeScene(event,"Record");
	}

	public void listenRecording(ActionEvent event) throws IOException{
		Worker = new ListenerWorker();
		new Thread(Worker).start();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		wrongAns.setText(recordController.getWrongAns());
	}
}
