package application.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.Score;
import application.model.ScoreModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreController extends AbstractController implements Initializable {
	@FXML
	private TableView<Score>table;
	private ScoreModel model = new ScoreModel();
	@FXML
	private TableColumn<Score, Integer> Correct = new TableColumn("Correct");
	@FXML
	private TableColumn<Score, Integer> Wrong =  new TableColumn("Wrong");
	@FXML
	private TableColumn<Score, String> Username =  new TableColumn("User");
	private static boolean alreadyExecuted;

	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	
	
	/**
	 * The following method reads the scores.txt and stores the information of previous scores into the 
	 * score model so that it can be displayed in the score table,the method is only required to be called once
	 * per start-up of the application.
	 */
	public void addPreviousScores() {
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Correct.setCellValueFactory(new PropertyValueFactory<Score, Integer>("correct"));
		Wrong.setCellValueFactory(new PropertyValueFactory<Score, Integer>("wrong"));
		Username.setCellValueFactory(new PropertyValueFactory<Score, String>("username"));
		table.setItems(model.getData());
	}



}
