package exception;

public class NegativeNumberException extends Exception{
	
	public NegativeNumberException() {
		super("No se aceptan numeros negativos");
	}
	
}
