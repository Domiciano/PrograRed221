package model;

import java.util.ArrayList;

public class PersonReg {

	private static PersonReg instance;
	
	
	public static synchronized PersonReg getInstance() {
		if(instance == null) {
			instance = new PersonReg();
		}
		return instance;
	}
	
	
	private PersonReg() {
		data = new ArrayList<>();
	}

	//Global
	
	private OnDataChangedListener listener;
	
	private ArrayList<Person> data;
	
	public void addPerson(Person person) {
		data.add(person);
		listener.onDataChanged();
	}
	
	public ArrayList<Person> getData(){
		return data;
	}
	
	public void setListener(OnDataChangedListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnDataChangedListener{
		void onDataChanged();
	}
	
	
}
