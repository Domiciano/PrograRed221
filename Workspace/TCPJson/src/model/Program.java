package model;

import java.util.ArrayList;

public class Program {
	
	public String type="Program";
	
	private String name;
	private ArrayList<Course> courses;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	
	
	
}
