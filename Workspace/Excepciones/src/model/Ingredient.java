package model;

import exception.NegativeNumberException;

public class Ingredient {
	
	private int name;
	private int quantity;
	
	public void addQuantity(int number) throws NegativeNumberException{
		if(number<0) {
			throw new NegativeNumberException();
		}else {
			quantity += number;	
		}
		
	}
	
	
}
