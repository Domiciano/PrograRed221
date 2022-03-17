package model;

public class Victory {

	public String type = "Victory";
	private String beated;
	
	public Victory(String beated) {
		this.beated = beated;
	}
	
	public String getBeated() {
		return beated;
	}
	
	public void setBeated(String beated) {
		this.beated = beated;
	}
}