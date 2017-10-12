package application.view;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.controlsfx.control.Notifications;

//import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import application.model.NumberGenerator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController extends AbstractController  {

	public void changeSceneToInstructions(ActionEvent event) throws IOException{
		
		changeScene(event,"Instructions");
	}

	public void changeSceneToScore(ActionEvent event) throws IOException{
		changeScene(event,"scores");
	}	
	
	public void changeSceneToPracticeNumbers(ActionEvent event) throws IOException{
		changeScene(event,"PracticeNumbers");
	}
	
	public void changeSceneToPracticeModule(ActionEvent event) throws IOException{
		changeScene(event,"PraticeModule");
	}
	
	public void changeSceneToPracticeEquations(ActionEvent event) throws IOException{
		changeScene(event,"PracticeEquations");
	}
	


}
