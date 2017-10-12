package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PraticeModuleController extends AbstractController {
	@FXML
	TextField Number;
	private static int praticeNumber;
	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	public void changeSceneToPraticeRecord(ActionEvent event) throws IOException{
		this.praticeNumber = Integer.parseInt(Number.getText());
		changeScene(event,"PraticeRecord");
	}
	
	public int getpraticeNumber() {
		return this.praticeNumber;
	}

}
