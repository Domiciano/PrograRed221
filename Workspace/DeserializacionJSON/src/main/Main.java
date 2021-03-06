package main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) throws IOException{
		
		Scanner scan = new Scanner(System.in);
		String artist = scan.nextLine();
		String site = "https://api.deezer.com/search?q="+artist;
		
		URL url = new URL(site);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		InputStream is = conn.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int readBytes = 0;
		byte[] buffer = new byte[1024];
		while( (readBytes = is.read(buffer)) != -1) {
			baos.write(buffer, 0, readBytes);
		}
		is.close();
		baos.close();
		
		String json = new String(baos.toByteArray(), "UTF-8");
	
		System.out.println(json);
		
		Gson gson = new Gson();
		Search s = gson.fromJson(json, Search.class);
		for(Track track : s.data) {
			System.out.println(track.title);
			System.out.println(track.artist.name);
			System.out.println(track.artist.picture);
			System.out.println(track.album.title);
			System.out.println(track.album.cover);
			System.out.println(track.link);
			System.out.println();
		}
		
	}

}
