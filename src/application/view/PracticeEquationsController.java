package application.view;

import java.io.IOException;

import application.model.Equation;
import javafx.event.ActionEvent;

public class PracticeEquationsController extends AbstractController{

	RecordController recController = new RecordController();

	public void easyEq(ActionEvent event) throws IOException{
		Equation eq = new Equation();
		eq.setEasy();
		equation(event);
	}
	
	public void hardEq(ActionEvent event) throws IOException{
		Equation eq = new Equation();
		eq.setHard();
		equation(event);
	}
	
	public void equation(ActionEvent event) throws IOException{
		recController.setQTypeEquation();
		changeSceneToRecord(event);
	}
	
	public void changeSceneToRecord(ActionEvent event) throws IOException{
		CorrectController cc = new CorrectController();
		cc.setMaxQ(10);
		WrongAgainController wac = new WrongAgainController();
		wac.setMaxQ(10);
		changeScene(event,"Record");
	}
	
	public void quit(ActionEvent event) throws IOException{
		changeScene(event,"Menu");
	}

	
	public void viewCustomEq(ActionEvent event) throws IOException{
		
		changeScene(event,"CustomEquations");
		
	}
} 
