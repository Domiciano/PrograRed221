package model;

public class Answer {

	public String type = "Answer";
	private double answer;
	private int i;

	public Answer() {}

	public Answer(double answer, int  i) {
		this.answer = answer;
		this.i = i;
	}

	public double getAnswer() {
		return answer;
	}

	public int getI() {
		return i;
	}
	
}
