package application.model;

public class Equation {
	private int operator;
	private int a;
	private int b; 
	private static int ans;
	private static StringBuilder equation;

	public void generateNumbers() {
		 a = (int) (Math.random() * 10) + 1;
		 b = (int) (Math.random() * 10) + 1;
		 
	}
	public void generateExpression() {
		equation = new StringBuilder();
		operator = (int) (Math.random() * 4) + 1;
		generateNumbers();
	    //generate/append the operator and calculate the real answer
	    if (operator == 1) {
		    equation.append(a);
	        equation.append(" + ");
	        ans = a+b;
	    } else if (operator == 2) {
	    		while (b>=a) {
	    			generateNumbers();
	    			System.out.println(getEquation());
	        } 
	    		equation.append(a);
    			equation.append(" - ");
    			ans = a-b;
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
	}
	
//	public String calculateAns() {
//		
//	}

	public String getEquation() {
		return equation.toString();
	}
	
	public int getAns() {
		return ans;
	}
	
}
