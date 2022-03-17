package model;

public class Disconnect {

	
	public String type = "Disconnect";
	private boolean byRejection;
	
	public Disconnect(boolean b) {
		byRejection = b;
		
	}
	public boolean isByRejection() {
		return byRejection;
	}
	
}
