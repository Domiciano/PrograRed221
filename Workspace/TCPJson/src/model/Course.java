package model;

import java.util.*;

public class Course {
	
	
	public String type="Course";
	
	private String NRC;
	private String name;
	private int credits;
	private ArrayList<Teacher> teachers;
	private ArrayList<Student> students;
	
	public String getNRC() {
		return NRC;
	}
	public void setNRC(String nRC) {
		NRC = nRC;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(ArrayList<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	
	
	
	
}
