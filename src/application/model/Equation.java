package application.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Equation {
	private static int ans;
	private static StringBuilder equation;

	private enum Difficulty{
		EASY, HARD
	}

	private static Difficulty difficulty;

	public void setEasy() {
		difficulty = Difficulty.EASY;
	}

	public void setHard() {
		difficulty = Difficulty.HARD;
	}


	public void generateEquation() {
		if (difficulty == Difficulty.EASY) {
			generateEasyEquation();
		} else {
			generateHardEquation();
		}
	}

	public int generateEasyNumber() {
		return (int) (Math.random() * 10) + 1;
		//		b = (int) (Math.random() * 10) + 1; 
	}

	public int generateHardNumber() {
		return (int) (Math.random() * 99) + 1;
	}

	// create a random equation with 1~3 operators
	public void generateHardEquation() {
		String temp = "";
		while (!equationIsValid(temp)) {
			temp = "";

			// randomize left side
			if (Math.random()>0.5) {
				temp = generateEasyEquation();
			} else {
				temp = temp + generateHardNumber();
			}

			// decide if add or subtract two halves
			if (Math.random()>0.5) {
				temp = temp +" + ";
			} else {
				temp = temp + " - ";
			}

			// randomize right side
			if (Math.random()>0.5) {
				temp = temp + generateEasyEquation();
			} else {
				temp = temp + generateHardNumber();
			}
			System.out.println(temp);

		}
	}

	// checks if the equation is a legitamate equation and returns a boolean
	public boolean equationIsValid(String eq) {
		Object result = null;
		equation = new StringBuilder();
		if (eq.equals("")) {
			return false;
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			result = engine.eval(eq.replace("x", "*"));
		} catch (ScriptException e) {}	
		if (99<(Integer) result || 1>(Integer) result) {
			return false;
		} else {
			equation.append(eq);
			ans = (int) result;
			return true;
		}
	}

	// generate a string consisting of two numbers between 1-9
	// with a random operand in between them
	public String generateEasyEquation() {
		equation = new StringBuilder();
		int operator = (int) (Math.random() * 4) + 1;
		int a = generateEasyNumber();
		int b = generateEasyNumber();
		//generate/append the operator and calculate the real answer
		if (operator == 1) {
			equation.append(a);
			equation.append(" + ");
			ans = a+b;
			equation.append(b);
			return "( "+equation.toString()+" )";
		} else if (operator == 2) {
			while (b>=a) {
				a = generateEasyNumber();
				b = generateEasyNumber();
			} 
			equation.append(a);
			equation.append(" - ");
			ans = a-b;
			equation.append(b);
			return "( "+equation.toString()+" )";
		} else if (operator == 3) {
			equation.append(a);
			equation.append(" x ");
			ans = a*b;
		} else if (operator == 4) {
			a = a*b;
			equation.append(a);
			equation.append(" / ");
			ans = a/b;
		}
		equation.append(b);

		return equation.toString();
	}

	public String getEquation() {
		return equation.toString();
	}

	public int getAns() {
		return ans;
	}

}
