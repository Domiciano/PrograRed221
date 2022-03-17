package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

import com.TCPConnectionServer;
import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		TCPConnectionServer tcp = TCPConnectionServer.getInstance();
		tcp.start();
		

	}

}
