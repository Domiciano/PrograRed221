package model;

public class Answer {

	public String type = "Answer";
	private int qid;
	private double number;
	
	public Answer(int i, double n) {
		
		qid = i;
		number = n;
	}
	
	public int getQid() {
		return qid;
	}
	public double getNumber() {
		return number;
	}
	
}
