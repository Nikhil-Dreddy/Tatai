package application.view;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomEquationController extends AbstractController{
	
	@FXML
	private Label label = new Label();
	
	private Object result;

	
	public void quit(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
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
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			 result = engine.eval(label.getText().replace("x", "*"));
		} catch (ScriptException e) {}
		
		if (result == null) {
			// if equation sucks...
			System.out.println("wtf nikil stop getting carried");
		} else {
			System.out.println(result);
			label.setText("");
		}
	}
}
