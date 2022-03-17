package model;

public class User {

	private String id;
	private User opponent;
	@SuppressWarnings("unused")
	private String type = "User";
	
	public User(String id) {
		this.id = id;
	}
	public User() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getOpponent() {
		return opponent;
	}
	public void setOpponent(User opponent) {
		this.opponent = opponent;
	}	
}
