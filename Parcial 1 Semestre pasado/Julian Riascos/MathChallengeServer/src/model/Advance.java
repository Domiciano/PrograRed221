package model;

public class Advance {

	public String type = "Advance";
	private int index;
	
	public Advance(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
}