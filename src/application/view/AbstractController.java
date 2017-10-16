package application.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import application.model.ScoreModel;
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
	public void addPreviousScores() {
	 ScoreModel model = new ScoreModel();
		File score = new File("scores.txt");
		BufferedReader reader;
		ArrayList<String> scores = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(score));
			String line = reader.readLine();
			while(line != null) {
				scores.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i =0;i<scores.size();i++) {
			String s = scores.get(i);
			String[] words = s.split("\\s+");		
			model.addNewScore(words[0], Integer.parseInt(words[1]));
		}

	}
}
