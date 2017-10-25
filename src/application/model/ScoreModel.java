package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoreModel {
	private int correct;
	public static String username;
	private int wrong;
	private static ObservableList<Score> data = FXCollections.observableArrayList();

	/**
	 * The following method adds in a new score into the score model,all that's required is 
	 * the number of words pronounced correctly a score object is instainted and added into the 
	 * array list. 
	 * @param score
	 */
	public void addNewScore(int score) {
		Score A = new Score(username,score);
		data.add(A);
	}
	
	/**
	 * The following method adds in a new score into the score arraylist is data model. 
	 * @param username
	 * @param score
	 */
	public void addNewScore(String username,int score) {
		Score A = new Score(username,score);
		data.add(A);
	}
	
	/**
	 * This method returns the observable array list which contains all the scores of the current session.
	 * @return
	 */
	public ObservableList<Score> getData() {
		return data;
	}

}
