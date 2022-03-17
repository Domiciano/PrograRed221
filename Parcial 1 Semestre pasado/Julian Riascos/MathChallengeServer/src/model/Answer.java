package model;

public class Answer {

	public String type = "Answer";
	private double answer;
	private int index;

	public Answer(double answer, int index) {
		this.answer = answer;
		this.index = index;
	}

	public double getAnswer() {
		return answer;
	}

	public void setAnswer(double answer) {
		this.answer = answer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
