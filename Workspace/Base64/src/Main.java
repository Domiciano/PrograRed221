import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Main {

	public static void main(String[] args) throws IOException {
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
		
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] bytescoded = encoder.encode(baos.toByteArray());
		
		
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] original = decoder.decode(bytescoded);
		
		
		FileOutputStream fos = new FileOutputStream(new File("/Users/domicianorincon/Documents/Por enviar/omega.png"));
		
		fos.write(original);
		fos.close();
		
		
		
		String alfa = new String(bytescoded);
		System.out.println(alfa);
		
		
		
		

	}

}
