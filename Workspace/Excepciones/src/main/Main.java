package main;

import java.util.Scanner;

import exception.NegativeNumberException;
import model.Ingredient;

public class Main {

	public static void main(String[] args) {

		// 2 tipos de exceciones

		// 1. Las que ya estan definidas

		Scanner scan = new Scanner(System.in);

		Ingredient tomate = new Ingredient();

		while (true) {

			try {

				System.out.println("Cuanto quiere agregar");
				String line = scan.nextLine();
				int quanty = Integer.parseInt(line);

				tomate.addQuantity(quanty);
				
			} catch (NumberFormatException ex) {
				System.out.println("No digitó un numero");
			} catch(NegativeNumberException ex) {
				System.out.println("No digitó una opcion válida");
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}

		}

		// 2. Las definas por el prgrmador

	}

}
