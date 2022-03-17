package model;

public class Win {
	private String type = "Winner";
	private String loser;
	public Win() {}
	
	public Win(String loser) {
		this.loser = loser;
	}

	public String getLoser() {
		return loser;
	}

	public void setLoser(String loser) {
		this.loser = loser;
	}
	
}
