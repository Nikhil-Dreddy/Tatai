package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;

public class InstructionsController extends AbstractController{
	
	//Swaps to the menu scene
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

}
