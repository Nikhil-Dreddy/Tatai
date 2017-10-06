package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoreModel {
	private int correct;
	private static int iteration;
	private int wrong;
	private static ObservableList<Score> data = FXCollections.observableArrayList();

	public void addNewScore(int score) {
		Score A = new Score(iteration++,score);
		data.add(A);
	}

	public ObservableList<Score> getData() {
		return data;
	}

}
