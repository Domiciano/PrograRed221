package model;

public class Rejected {

	public String type = "Rejected";
	private String why;
	
	public Rejected(String why) {
		this.why = why;
	}

	public String getWhy() {
		return why;
	}

	public void setWhy(String why) {
		this.why = why;
	}
	
}
