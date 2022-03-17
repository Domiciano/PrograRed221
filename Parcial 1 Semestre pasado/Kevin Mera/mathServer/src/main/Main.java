package main;

import com.TCPConnectionServer;

public class Main {

	public static void main(String[] args) {
		TCPConnectionServer tcp = TCPConnectionServer.getInstance();
		tcp.start();
		

	}

}
