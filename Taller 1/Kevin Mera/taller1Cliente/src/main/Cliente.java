package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				
				System.out.println("\n* Type a command: RTT/Speed/WhatTimeIsIt/Interface/remoteIpConfig/ExitClient *");
				String line = sc.nextLine();

				System.out.println("(Client) Sending request...");
				Socket socket = new Socket("127.0.0.1",5000);
				System.out.println("(Client) Connected");

				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bwritter = new BufferedWriter(osw);

				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader breader = new BufferedReader(isr);

				//Recibir mensaje del server
				if (line.equalsIgnoreCase("RTT")) {

					String alfa = new String(new byte[1024]);
					double start = System.nanoTime();

					//Envio el identificador RTT
					bwritter.write(line+"\n");
					bwritter.flush();

					//Envio la linea alfa para ser procesada en el server
					bwritter.write(alfa+"\n");
					bwritter.flush();

					String line2 = breader.readLine();
					System.out.println("(Client) Message received: "+line2);

					double elapsedTime = System.nanoTime() - start;
					double elapsedTimeFinal = elapsedTime /1000000000;
					System.out.println("Time to RTT is: "+elapsedTimeFinal+" seconds");

				} else if(line.equalsIgnoreCase("speed")) {

					String bravo = new String(new byte[8192]);
					double start = System.nanoTime();

					//Envio el identificador Speed
					bwritter.write(line+"\n");
					bwritter.flush();

					//Envio la linea bravo para ser procesada en el server
					bwritter.write(bravo+"\n");
					bwritter.flush();

					String line2 = breader.readLine();
					System.out.println("(Client) Message received: "+line2);

					double elapsedTime = System.nanoTime() - start;
					double elapsedTimeFinal = elapsedTime /1000000000;
					double speedTransmission = (bravo.getBytes().length / 1024) / elapsedTimeFinal;
					System.out.println("(Client) Speed time is: "+speedTransmission+"Kbs/s");

				} 
				else if(line.equalsIgnoreCase("ExitClient")) {

					System.out.println("(Client) *** Client is closed by command ***");
					socket.close();
					sc.close();
					break;

				} else {

					//Envio el identificador 
					bwritter.write(line+"\n");
					bwritter.flush();
					String line2 = breader.readLine();
					System.out.println("(Client) Message received: "+line2);
				}

				socket.close();
				System.out.println("(Client) Client Disconnected from the server");

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}