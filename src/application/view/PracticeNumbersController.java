package application.view;

import java.io.IOException;

import application.model.NumberGenerator;
import javafx.event.ActionEvent;

public class PracticeNumbersController extends AbstractController{

	NumberGenerator number = new NumberGenerator();
	RecordController recController = new RecordController();
	
	public void easyButtonClicked(ActionEvent event) throws IOException{
		number.setDifficultyToEasy();
		recController.setQTypeQuestion();
		changeSceneToRecord(event);
	}


	public void hardButtonClicked(ActionEvent event) throws IOException{
		number.setDifficultyToHard();
		recController.setQTypeQuestion();
		changeSceneToRecord(event);
	}
	
	// Change scene to record scene when button is pushed
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		changeScene(event,"Record");
	}
	
	
	public void changeSceneToUsername(ActionEvent event) throws IOException{
		changeScene(event,"Record");
	}
	
	public void quit(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
}
