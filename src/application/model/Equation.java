package application.model;

public class Equation {
	private int operator;
	private int a;
	private int b; 
	StringBuilder equation = new StringBuilder();

	public void generateNumbers() {
		 operator = (int) (Math.random() * 4) + 1;
		 a = (int) (Math.random() * 10) + 1;
		 b = (int) (Math.random() * 10) + 1;
		 
	}
	public void generateExpression() {
		generateNumbers();
	    //append the first number
	    equation.append(a);
	    //generate/append the operator and calculate the real answer
	    if (operator == 1) {
	        equation.append(" + ");
	    } else if (operator == 2) {
	        equation.append(" - ");
	    } else if (operator == 3) {
	        equation.append(" x ");
	    } else if (operator == 4) {
	        if ((a%b==0) && (a>b)) {
	        } else {
	            generateNumbers();
	        }
	        equation.append(" / ");

	    }
	    // fk
	    equation.append(b);
	}
	
//	public String calculateAns() {
//		
//	}

	public String getEquation() {
		return equation.toString();
	}
}
