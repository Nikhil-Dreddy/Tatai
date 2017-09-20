package application.model;

import java.util.Random;

public class NumberGenerator {
	private static Difficulty difficulty;
	private static int num;
	
	private enum Difficulty{
		EASY, HARD
	}
	
	public void setDifficultyToEasy(){
		difficulty = Difficulty.EASY;
	}
	
	public void setDifficultyToHard(){
		difficulty = Difficulty.HARD;
	}
	
	public void generateNum(){
		Random rand = new Random();
		if (difficulty == Difficulty.EASY){
			num =  rand.nextInt(9) + 1;
		} else if(difficulty == Difficulty.HARD){
			num = rand.nextInt(99) + 1;
		}
	}
	
	public int getNum(){
		return num;
	};
	
}
