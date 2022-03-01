package main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		int A = 192;
		int B = 168;
		int C = 30;
		int D = 15;
		
		int IP = 0;
		IP = IP|D;
		IP = IP|(C<<8);
		IP = IP|(B<<16);
		IP = IP|(A<<24);
		System.out.println(Integer.toBinaryString(IP));
		
		
		//IP
		int Aprima = (IP>>24)&0xFF;
		System.out.println(Aprima);
		int Bprima = (IP>>16)&0xFF;
		System.out.println(Bprima);
		int Cprima = (IP>>8)&0xFF;
		System.out.println(Cprima);
		int Dprima = IP&0xFF;
		System.out.println(Dprima);
		
		try {
			InetAddress daniel = InetAddress.getByName("172.30.148.237");
			System.out.println(daniel.isReachable(1000));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
