package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;

public class InstructionsController extends AbstractController{
	
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

}
