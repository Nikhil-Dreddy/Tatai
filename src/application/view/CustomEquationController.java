package application.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class CustomEquationController extends AbstractController{
	
	@FXML
	private Label label = new Label();
	
	public void viewEquations(ActionEvent event) throws IOException{
		changeScene(event,"ViewCustomEquations");
	}
	
	public void back(ActionEvent event) throws IOException{
		changeScene(event,"PracticeEquations");
	}	
	
	public void one() {
		appendText("1");
	}
	public void two() {
		appendText("2");
	}
	public void three() {
		appendText("3");
	}
	public void four() {
		appendText("4");
	}
	public void five() {
		appendText("5");
	}
	public void six() {
		appendText("6");
	}
	public void seven() {
		appendText("7");
	}
	public void eight() {
		appendText("8");
	}
	public void nine() {
		appendText("9");
	}
	public void zero() {
		appendText("0");
	}
	public void openBrac() {
		appendText("(");
	}
	public void closeBrac() {
		appendText(")");
	}
	public void plus() {
		appendText("+");
	}
	public void minus() {
		appendText("-");
	}
	public void divide() {
		appendText("/");
	}
	public void times() {
		appendText("x");
	}
	public void ac() {
		label.setText("");
	}
	public void del() {
		String str = label.getText();
		if (str != null && str.length() > 0) {
	        str = str.substring(0, str.length() - 1);
	    }
		label.setText(str);
	}
	
	public void appendText(String newText) {
        label.setText(label.getText() + newText);
    }
	
	public void submit() {
		Object result = null;

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			 result = engine.eval(label.getText().replace("x", "*"));
		} catch (ScriptException e) {}		
		if (result == null || result.equals("Infinity")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erroe");
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
		} else if (equationExists(label.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Equation already exists");
			alert.setContentText("Please enter a new equation");
			alert.showAndWait();
		} else {
			saveEq(label.getText());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Succesfully created the following equation:");
			alert.setContentText(label.getText());
			alert.showAndWait();
		}
		label.setText("");
	}
	
	public boolean equationExists(String s) {

		File inputFile = new File("custom_equations.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String currentLine;

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
	
	public void saveEq(String s) {
		{
			try {
				Writer output;
				output = new BufferedWriter(new FileWriter("custom_equations.txt", true));
				output.append(s+"\n");
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
