package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
* Gives all controllers the ability to change scenes at the push of a button
*/
public abstract class AbstractController {
	
	public void changeScene(ActionEvent event, String scene) throws IOException{
		Parent menuViewParent = FXMLLoader.load(getClass().getResource(scene+".fxml"));
		Scene menuViewScene = new Scene(menuViewParent);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(menuViewScene);
		stage.show();
	}
}
