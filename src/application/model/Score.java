package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {
	//The three fields stored by the score model
	private SimpleIntegerProperty correct;
	private SimpleStringProperty username;
	private SimpleIntegerProperty wrong;

	//the score constructor which takes in the score and username and converts into the right property files	
	public Score(String username,int score) {
		this.correct = new SimpleIntegerProperty(score);
		this.username = new SimpleStringProperty(username);
		this.wrong = new SimpleIntegerProperty(10 - score);
	}
	
	//getter methods for each of the individual fields of the score object
	public int getCorrect()	{return correct.get();}
	public int getWrong() {return wrong.get();}
	public String getUsername() {return username.get();}
}