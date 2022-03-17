package model;

public class Question {

	private String type = "Question";
	private double result;
	private int i;
	private String question;
	
	public Question(String q, double result, int i) {
		this.question = q;
		this.result = result;
		this.i = i;
	}

	public double getResult() {
		return result;
	}

	public int getI() {
		return i;
	}

	public String getQuestion() {
		return question;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	
}
