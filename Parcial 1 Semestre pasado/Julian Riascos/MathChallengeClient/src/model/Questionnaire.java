package model;

public class Questionnaire {

	public enum OPERATOR {SUMA,RESTA,DIVISION,MULTIPLICACION};
	public double result;
	
	public Questionnaire() {}
	
	public String question() {
		int random = (int) (3*Math.random());
		int x = (int) (100*Math.random());
		int y = (int) (100*Math.random());
		OPERATOR[] sign = OPERATOR.values();
		String operation = x + " " + sign[random] + " " + y;
		switch (sign[random]) {
		case SUMA:
			operation = x + " + " + y;
			result = x + y;
			break;
		case RESTA:
			operation = x + " - " + y;
			result = x - y;
			break;
		case MULTIPLICACION:
			operation = x + " x " + y;
			result = x * y;
			break;
		case DIVISION:
			if (y == 0) y += 1;
			operation = x + " / " + y;
			double q = (double) x, w = (double) y;
			result = (Math.round((q/w)*100))/100d;
			break;
		default:
			break;
		}
		return operation;
	}
	
	public double getResult() {
		return result;
	}
}
