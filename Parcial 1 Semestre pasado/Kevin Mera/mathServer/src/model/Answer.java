package model;

public class Answer {

	public String type = "Answer";
	private String answer;
	private String id;

	public Answer() {}

	public Answer(String id, String answer) {
		this.answer = answer;
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public String getId() {
		return id;
	}

	
}
