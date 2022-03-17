package model;

public class Question {

	private String type = "Question";
	private int result;
	private int i;
	private String question;
	
	public Question(String q, int r, int i) {
		this.question = q;
		this.result = r;
		this.i = i;
	}

	public int getResult() {
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
