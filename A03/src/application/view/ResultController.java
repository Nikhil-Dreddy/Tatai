package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultController extends AbstractController{
	

	@FXML
	private Label scoreLabel;
	
	@FXML
	private Label nextLevelLabel;
	
	// for the quit button
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	
}
