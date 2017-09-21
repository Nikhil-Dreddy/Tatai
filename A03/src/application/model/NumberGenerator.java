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
	
	public String digitToMaori(int i){
		if(i == 1){
			return "Tahi";
		}
		if(i == 2){
			return "Rua";
		}
		if(i == 3){
			return "Toru";
		}
		if(i == 4){
			return "Wha";
		}
		if(i == 5){
			return "Rima";
		}
		if(i == 6){
			return "Ono";
		}
		if(i == 7){
			return "Whitu";
		}
		if(i == 8){
			return "Waru";
		}
		if(i == 9){
			return "Iwa";
		}
		return "";
	}
	
	public String getMaoriNum(){
		int secondDigit = num % 10;
		int firstDigit = (num/10) % 10;
		
		if (firstDigit == 0){
			return digitToMaori(secondDigit);
		} else if (firstDigit == 1){
			return "Tekau Ma "+digitToMaori(secondDigit);
		} else if(secondDigit == 0){
			return digitToMaori(firstDigit)+" Tekau";
		} else {
			return digitToMaori(firstDigit)+" Tekau Ma "+digitToMaori(secondDigit);
		}
	}
	
	public int getNum(){
		return num;
	};
	
}
