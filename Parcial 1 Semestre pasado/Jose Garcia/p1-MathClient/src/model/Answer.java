package model;

public class Answer {
	public String type = "Answer";
	private int question;
	private double answer;
	
	public Answer() {};
	public Answer(double answer, int question) {
		this.setAnswer(answer);
		this.setQuestion(question);
	}
	public double getAnswer() {
		return answer;
	}
	public void setAnswer(double answer) {
		this.answer = answer;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	
}
