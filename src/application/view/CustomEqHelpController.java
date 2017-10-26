package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;

public class CustomEqHelpController extends AbstractController{
	public void back(ActionEvent event) throws IOException{
		changeScene(event,"CustomEquations");
	}
}
