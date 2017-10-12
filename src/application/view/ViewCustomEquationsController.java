package application.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ViewCustomEquationsController extends AbstractController implements Initializable{

	@FXML
	private ListView<String> listView;
	//	ObservableList<String> items = FXCollections.observableArrayList();

	public void back(ActionEvent event) throws IOException{
		changeScene(event,"PracticeEquations");
	}

	public void delete() {
		ObservableList<String> equations = listView.getSelectionModel().getSelectedItems();
		for (String eq : equations) {
			try {

				File inputFile = new File("custom_equations.txt");
				File tempFile = new File("temp.txt");

				BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

				String currentLine;


				while((currentLine = reader.readLine()) != null) {
					String trimmedLine = currentLine.trim();
					if(trimmedLine.equals(eq)) continue;
					writer.write(currentLine + System.getProperty("line.separator"));
				}
				writer.close(); 
				reader.close(); 
				boolean successful = tempFile.renameTo(inputFile);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		listView.getItems().removeAll(equations);
	}

	public void add(ActionEvent event) throws IOException {
		changeScene(event,"CustomEquation");
	}

	public void createQuestionList() {
		//create custom question list
	}

	public void initializeListView() {
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("custom_equations.txt"));
			String line = null;  
			while ((line = br.readLine()) != null)  
			{  
				listView.getItems().add(line);
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeListView();
	}
}
