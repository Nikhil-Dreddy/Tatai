package application.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CustomEquationsController extends AbstractController implements Initializable{

	@FXML
	private TextField qNameTextField = new TextField();
	@FXML
	private TextField addEqTextField = new TextField();

	@FXML
	private TreeView<String> treeView;

	private TreeItem<String> root = new TreeItem<>("root");

	private static ArrayList<String> equationList;
	private final String customQFileName = "custom_questionaires/";

	public void back(ActionEvent event) throws IOException{
		changeScene(event,"PracticeEquations");
	}

	// allows user to select a questionaire from the listview and play
	public void play(ActionEvent event) throws IOException{
		equationList = new ArrayList<String>();
		ResultController.setCustom(true);

		try {
			TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
			if(item != null) {
				File file;
				if (item.getParent().equals(root)) {
					file = new File(customQFileName+item.getValue());
				} else {
					file = new File(customQFileName+item.getParent().getValue());
				}
				BufferedReader br = new BufferedReader(new FileReader(file));
				br = new BufferedReader(new FileReader(file));
				String line = null;  
				while ((line = br.readLine()) != null){  
					equationList.add(line);
				}
				// if questionaire contains at least one question
				if (equationList.size() != 0) {
					RecordController rc = new RecordController();
					rc.setQTypeCustom();
					CorrectController cc = new CorrectController();
					cc.setMaxQ(equationList.size());
					WrongAgainController wac = new WrongAgainController();
					wac.setMaxQ(equationList.size());
					changeScene(event, "Record");
				// if questionaire is empty
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("Questionaire contains no equations");
					alert.setContentText("Please add questions to the questionaire");
					alert.showAndWait();

				}
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getEquation() {
		RecordController rc = new RecordController();
		return equationList.get(rc.getQno());
	}

	// user chooses either questionaire or equation to delete
	public void delete() {
		try {
			TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
			if(item != null) {
				// if user chooses questionaire
				if (item.getParent().equals(root)) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Deleating Questionaire");
					alert.setContentText("Are you sure you want to delete : "+item.getValue()+" ?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						File file = new File(customQFileName+item.getValue());
						file.delete();
						boolean remove = item.getParent().getChildren().remove(item);
					}
				// if user chooses equation
				} else {
					File inputFile = new File(customQFileName+item.getParent().getValue());
					File tempFile = new File(customQFileName+"temp");

					BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

					String currentLine;

					while((currentLine = reader.readLine()) != null) {
						String trimmedLine = currentLine.trim();
						if(trimmedLine.equals(item.getValue())) continue;
						writer.write(currentLine + System.getProperty("line.separator"));
					}
					writer.close(); 
					reader.close(); 
					boolean successful = tempFile.renameTo(inputFile);
					boolean remove = item.getParent().getChildren().remove(item);


				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("hi");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// reads the textfield where user enters an equation and saves it
	// user must first choose which questionaire they want to add to
	public void addEquation() {
		Object result = null;
		String newEq = addEqTextField.getText();

		TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		// if user doesnt choose a questionaire
		if (item == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("No questionaire chosen");
			alert.setContentText("Please select the questionaire in which you want to add an equation");
			alert.showAndWait();
		} else {
			try {
				result = engine.eval(newEq.replace("x", "*"));
			} catch (ScriptException e) {}		
			if (result == null || result.equals("Infinity")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Equation");
				alert.setContentText("Please write a valid equation");
				alert.showAndWait();
			} else if (Double.valueOf(result.toString()) != Math.floor(Double.valueOf(result.toString()))){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Answer contains decimals");
				alert.setContentText("Answer must be a whole number");
				alert.showAndWait();
			} else if (99<(Integer) result || 1>(Integer) result) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Answer out of bounds");
				alert.setContentText("Make sure answer is between 1~99 inclusive");
				alert.showAndWait();
			} else if (equationExists(newEq)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Equation already exists");
				alert.setContentText("Please enter a new equation");
				alert.showAndWait();
			} else {
				System.out.println(result);
				saveEq(newEq);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Succesfully created the following equation:");
				alert.setContentText(newEq);
				alert.showAndWait();
				addEqTextField.setText("");

			}
		}
	}
	
	// helper method which appends equation to the saved file
	public void saveEq(String s) {

		try {
			Writer output;
			TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
			if (item.getParent().equals(root)) {
				output = new BufferedWriter(new FileWriter(customQFileName+item.getValue(), true));
				createBranch(s, item, null);
			} else {
				output = new BufferedWriter(new FileWriter(customQFileName+item.getParent().getValue(), true));
				createBranch(s, item.getParent(), null);
			}
			output.append(s+"\n");
			output.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// checks in the treeView if a equation string exists
	public boolean equationExists(String s) {
		try {
			BufferedReader reader;
			String currentLine;

			TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
			if (item.getParent().equals(root)) {
				reader = new BufferedReader(new FileReader(customQFileName+item.getValue()));
			} else {
				reader = new BufferedReader(new FileReader(customQFileName+item.getParent().getValue()));
			}

			while((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				if(trimmedLine.equals(s)) {
					reader.close(); 
					return true;
				}
			}
			reader.close(); 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	// reads the textfield for a new questionaire name 
	public void createQuestionList() {
		String qName = new String();
		qName = qNameTextField.getText();
		// check if questionaire name isnt taken or empty
		if (qName.length() != 0) {
			File f = new File("custom_questionaires/"+qName);
			if(!f.exists()) { 

				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				createBranch(qName, root, null);
				qNameTextField.setText("");
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("The questionaire \""+qName+"\" already exists");
				alert.setContentText("Please choose another name for your questionaire");
				alert.showAndWait();
			}
		}
	}

	// sets up the treeview when custom equation scene is opened
	public void 	initializeTreeView() {
		root.setExpanded(true);

		File[] files = new File(customQFileName).listFiles();
		for (File file : files) {
			createBranch(file.getName(), root, customQFileName+file.getName());
		}

		treeView.setRoot(root);
		treeView.setShowRoot(false);
	}

	// helper method to create a new child for a given parent
	public TreeItem<String> createBranch(String s, TreeItem<String> parent, String file){
		TreeItem<String> item = new TreeItem<String>(s);
		parent.getChildren().add(item);

		if (file != null) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = null;  
				while ((line = br.readLine()) != null)  
				{  
					createBranch(line, item, null);
				} 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  		
		}
		return item;
	}

	public void help(ActionEvent event) throws IOException{
		changeScene(event,"CustomEqHelp");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeTreeView();
	}
}
