package main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;

public class Main {

	public static void main(String[] args) throws Exception {
		String ruta = "/Users/domicianorincon/Documents/Por enviar/alfa.png";
		FileInputStream fis = new FileInputStream(new File(ruta));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int readBytes = 0;
		byte[] buffer = new byte[1024];
		while((readBytes = fis.read(buffer))!=-1) {
			baos.write(buffer,0,readBytes);
		}
		fis.close();
		baos.close();
		
	    Base64.Encoder enc = Base64.getEncoder();
	    byte[] encbytes = enc.encode(baos.toByteArray());
	    String msg64 = new String(encbytes);
	    System.out.println(msg64);
	    
	    //DECODE
	    Base64.Decoder dec = Base64.getDecoder();
	    byte[] decbytes = dec.decode(encbytes);
	    FileOutputStream fos = new FileOutputStream(new File("/Users/domicianorincon/Documents/Por enviar/beta.png"));
	    fos.write(decbytes);
	    fos.close();
	}

}
