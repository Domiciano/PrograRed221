package model;

public class CorrectAnswer {
	
	public String type = "CorrectAnswer";
	private double num1;
	private double num2;
	private char operator;
	private int nextQuestionID;
	
	public CorrectAnswer() {}

	public CorrectAnswer(double num1, double num2, char operator, int nextQuestionID) {
		this.num1 = num1;
		this.num2 = num2;
		this.operator = operator;
		this.nextQuestionID = nextQuestionID;
	}

	public double getNum1() {
		return num1;
	}

	public double getNum2() {
		return num2;
	}

	public char getOperator() {
		return operator;
	}

	public int getNextQuestionID() {
		return nextQuestionID;
	}
}
