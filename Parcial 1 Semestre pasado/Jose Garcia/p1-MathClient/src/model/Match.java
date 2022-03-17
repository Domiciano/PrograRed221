package model;

public class Match {

	public String type = "Match";
	private User oponentUser;
	private String[] questions;
	
	public Match() {}
	public Match(User oponentUser,String[] questions) {
		this.oponentUser=oponentUser;
		this.questions=questions;
	}

	public User getOponentUser() {
		return oponentUser;
	}
	public void setOponentUser(User oponentUser) {
		this.oponentUser = oponentUser;
	}
	public String[] getQuestions() {
		return questions;
	}
	public void setQuestions(String[] questions) {
		this.questions = questions;
	}
	
}
