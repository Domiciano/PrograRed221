package model;

public class MathBank {
	
	private int questionNum;
	private double num1;
	private double num2;
	private char operator;
	private String respuesta;
	private boolean resuelta;
	
	public MathBank(int questionNum, double num1, double num2, char operator, String respuesta) {
		this.questionNum = questionNum;
		this.num1 = num1;
		this.num2 = num2;
		this.operator = operator;
		this.respuesta = respuesta;
		this.resuelta = false;
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

	public String getRespuesta() {
		return respuesta;
	}

	public boolean isResuelta() {
		return resuelta;
	}

	public void setResuelta(boolean resuelta) {
		this.resuelta = resuelta;
	}
	
	public int getQuestionNum() {
		return questionNum;
	}
}
