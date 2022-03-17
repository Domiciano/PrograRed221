package main;

import com.TPCServer;

public class Main {
	public static void main(String[] args) {
		TPCServer tcp = TPCServer.getInstance();
		tcp.start();

	}

}
