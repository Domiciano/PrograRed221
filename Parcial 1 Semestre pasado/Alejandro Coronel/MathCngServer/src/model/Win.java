package model;

public class Win {

	public String type = "Win";
	private boolean win;
	
	public Win(boolean w) {
		win = w;
	}
	
	public boolean isWinner() {
		return win;
	}
}
