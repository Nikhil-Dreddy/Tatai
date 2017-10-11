package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {
	private SimpleIntegerProperty correct;
	private SimpleStringProperty username;
	private SimpleIntegerProperty wrong;

	public Score(String username,int score) {
		this.correct = new SimpleIntegerProperty(score);
		this.username = new SimpleStringProperty(username);
		this.wrong = new SimpleIntegerProperty(10 - score);
	}
	public int getCorrect() 
	{return correct.get();}
	public int getWrong() {return wrong.get();}
	public String getUsername() {return username.get();}
}