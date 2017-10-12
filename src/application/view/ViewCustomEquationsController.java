package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewCustomEquationsController extends AbstractController{
	
	@FXML
	private ListView list = new ListView();
	
	public void quit(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	public void delete() {
		// delete equation file
	}
	
	public void add() {
		// create new equation
	}
	
	public void createQuestionList() {
		//create custom question list
	}
}
