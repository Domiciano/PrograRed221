package model;

public class InitializeFirstQuestion {

	public String type = "InitializeFirstQuestion";
	private double num1;
	private double num2;
	private char operator;
	private int questionID;


	public InitializeFirstQuestion() {}


	public InitializeFirstQuestion(double num1, double num2, char operator, int questionID) {
		this.num1 = num1;
		this.num2 = num2;
		this.operator = operator;
		this.questionID = questionID;
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


	public int getQuestionID() {
		return questionID;
	}


}
