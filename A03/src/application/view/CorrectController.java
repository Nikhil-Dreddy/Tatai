package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;

public class CorrectController extends AbstractController{

	private RecordController rC = new RecordController();
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	// for the next number button
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		if(rC.getQno() == 10) {
			changeScene(event,"Result");
			rC.resetQno();
			rC.resetScore();
		}
		else {
			changeScene(event,"Record");
		}
	}

}
