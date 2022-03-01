package main;

public class Main {

	public static void main(String[] args) {
		//IP -> Int
		int A=192,B=168,C=0,D=15;
		int IP = 0;
		IP = IP|(A<<24);
		IP = IP|(B<<16);
		IP = IP|(C<<8);
		IP = IP|D;
		System.out.println(Integer.toBinaryString(IP));
		
		//Int -> IP
		int E = (IP>>24)&0x000000FF;
		int F = (IP>>16)&0x000000FF;
		int G = (IP>>8)&0x000000FF;
		int H = (IP>>0)&0x000000FF;
		System.out.println(E);
		System.out.println(F);
		System.out.println(G);
		System.out.println(H);
	}

}
