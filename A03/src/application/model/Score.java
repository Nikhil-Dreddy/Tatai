package application.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Score {
	private SimpleIntegerProperty correct;
	private SimpleIntegerProperty iteration;
	private SimpleIntegerProperty wrong;

	public Score(int Iteration,int score) {
		this.correct = new SimpleIntegerProperty(score);
		this.iteration = new SimpleIntegerProperty(Iteration);
		this.wrong = new SimpleIntegerProperty(10 - score);
	}
	public int getCorrect() 
	{return correct.get();}
	public int getWrong() {return wrong.get();}
	public int getIteration() {return iteration.get();}
}