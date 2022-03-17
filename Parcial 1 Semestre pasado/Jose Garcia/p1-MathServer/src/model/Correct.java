package model;

public class Correct {
	public String type = "Correct";
	private boolean win;

	public Correct() {};

	public Correct(boolean win) {
			this.setWin(win);
		}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

}