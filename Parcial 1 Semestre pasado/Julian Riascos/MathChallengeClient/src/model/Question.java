package model;

public class Question {

	public String type = "Question";
	private String question;
	private double result;
	private int index;
	
	public Question(String question, double result, int index) {
		this.question = question;
		this.result = result;
		this.index = index;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
