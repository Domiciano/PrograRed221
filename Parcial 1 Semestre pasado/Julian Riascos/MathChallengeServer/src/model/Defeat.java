package model;

public class Defeat {

	public String type = "Defeat";
	private String winner;
	
	public Defeat(String winner) {
		this.winner = winner;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
}