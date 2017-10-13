package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CorrectController extends AbstractController implements Initializable{

	private static int maxQ;
	private RecordController rC = new RecordController();
	@FXML
	private Button Continue;
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		rC.resetQno();
		rC.resetScore();
		changeScene(event,"Menu");
	}
	
	public void setMaxQ(int i) {
		maxQ = i;
	}

	// for the next number button
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(PraticeRecordController.praticeMode) {
			PraticeRecordController.praticeMode = false;
			changeScene(event,"PraticeModule");
		}
		else {
			if(rC.getQno() == maxQ) {
				changeScene(event,"Result");
				rC.resetQno();
				rC.resetScore();
			}
			else {
				changeScene(event,"Record");
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(PraticeRecordController.praticeMode) {
			Continue.setText("Return to Pratice Module");
		}
		
	}

}
