package model;

public class Person {

	private String natID;
	private String nombre;
	
	public Person(String natID, String nombre) {
		this.natID = natID;
		this.nombre = nombre;
	}
	
	public String getNatID() {
		return natID;
	}
	public void setNatID(String natID) {
		this.natID = natID;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
