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
	
	// generates a random number based on the level the user chooses
	public void generateNum(){
		Random rand = new Random();
		if (difficulty == Difficulty.EASY){
			num =  rand.nextInt(9) + 1;
		} else if(difficulty == Difficulty.HARD){
			num = rand.nextInt(99) + 1;
		}
	}
	
	public String digitToMaori(int i){
		if(i == 1){
			return "tahi";
		}
		if(i == 2){
			return "rua";
		}
		if(i == 3){
			return "toru";
		}
		if(i == 4){
			return "whaa";
		}
		if(i == 5){
			return "rima";
		}
		if(i == 6){
			return "ono";
		}
		if(i == 7){
			return "whitu";
		}
		if(i == 8){
			return "waru";
		}
		if(i == 9){
			return "iwa";
		}
		return "";
	}
	
	// Converts a int between 1-99 into a maori word
	public String getMaoriNum(int i){
		int secondDigit = i % 10;
		int firstDigit = (i/10) % 10;
		
		if (firstDigit == 0){
			return digitToMaori(secondDigit);
		} else if (i == 10) {
			return "tekau";
		} else if (firstDigit == 1){
			return "tekau maa "+digitToMaori(secondDigit);
		} else if(secondDigit == 0){
			return digitToMaori(firstDigit)+" tekau";
		} else {
			return digitToMaori(firstDigit)+" tekau maa "+digitToMaori(secondDigit);
		}
	}
	
	public int getNum(){
		return num;
	};
	
}
