package model;

public class Loser {
	@SuppressWarnings("unused")
	private String type = "Loser";
	private String w;
	@SuppressWarnings("unused")
	public Loser(String w) {
		this.w= w;
	}
	
	public Loser() {}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}
	
}
