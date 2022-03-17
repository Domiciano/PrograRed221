package model;

public class Reject {

	public String type = "Reject";
	private String reject;
	
	public Reject(String reject) {
		this.reject = reject;
	}

	public String getReject() {
		return reject;
	}

	public void setReject(String reject) {
		this.reject = reject;
	}
	
}
