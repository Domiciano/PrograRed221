package model;

public class Questionnaire {

	private String[] operations = { "SUMA", "RESTA", "DIVISION", "MULTIPLICACION" };
	private double result;

	public Questionnaire() {

	}

	public String randomQuestion() {		
		int selection = (int) (2 * Math.random());
		int x = (int) (100 * Math.random());
		int y = (int) (100 * Math.random());
		String operation = x + " " + " " + y;

		switch (operations[selection]) {
		case "SUMA":
			operation = x + " + " + y;
			result = x + y;
			break;

		case "RESTA":
			operation = x + " - " + y;
			result = x - y;
			break;

		case "DIVISION":
			if(y==0) {
				y++;
			}
			operation = x + " / " + y;
			result = x / y;
			break;

		case "MULTIPLICACION":
			operation = x + " * " + y;
			result = x * y;
			break;
		}
		return operation;
	}
	
	public double getResult() {
		return result;
	}

}
