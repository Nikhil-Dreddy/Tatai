package application.view;

import java.io.IOException;
import java.net.URL;
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
	private TableColumn<Score, Integer> It =  new TableColumn("Iteration");


	public void changeSceneToMenu(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Correct.setCellValueFactory(new PropertyValueFactory<Score, Integer>("correct"));
		Wrong.setCellValueFactory(new PropertyValueFactory<Score, Integer>("wrong"));
		It.setCellValueFactory(new PropertyValueFactory<Score, Integer>("Iteration"));
		table.setItems(model.getData());
	}



}
