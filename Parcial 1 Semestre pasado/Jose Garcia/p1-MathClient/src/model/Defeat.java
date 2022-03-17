package model;

public class Defeat {

	public String type="Defeat";
	private boolean defeat;

	public Defeat() {};

	public Defeat(boolean defeat) {
			this.setDefeat(defeat);
		}

	public boolean isDefeat() {
		return defeat;
	}

	public void setDefeat(boolean defeat) {
		this.defeat = defeat;
	}


}
