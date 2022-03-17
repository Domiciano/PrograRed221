package model;

public class Question {

	public String type= "Question";
	private String statement;
	private double answer;
	private int id;
	public Question(int i,String s, double a) {

		id = i;
		statement = s;
		answer = a;
	}

	public Question(int i2,String s2) {

		id = i2;
		statement = s2;
		
	}

	public String getStatement() {
		return statement;
	}

	public double getAnswer() {
		return answer;
	}
	public int getId() {
		return id;
	}

}
