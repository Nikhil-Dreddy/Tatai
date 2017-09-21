package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;

public class CorrectController extends AbstractController{
	
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	// for the next number button
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		changeScene(event,"Record");
	}

}
