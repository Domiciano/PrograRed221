package model;

public class Disconnect {

	public String type = "Disconnect";
	private String why;
	
	public Disconnect(String why) {
		this.why = why;
	}

	public String getWhy() {
		return why;
	}

	public void setWhy(String why) {
		this.why = why;
	}
	
}
