import java.io.IOException;
import java.util.LinkedList;

public class Program {
	public static void main(String[] args) throws IOException
	{
		String fileLocation = "C:\\facepalm.jpg";
        String encryptedFileDestination = "C:\\encodedFile.jpg";
        String decryptedFileDestination = "C:\\decodedFile.jpg";
        
        // get sample key (key must be valid!)
		LinkedList<Character> key = initCharKey();
		
		FileEncoder61715 encoder = new FileEncoder61715();
		
		long startTime = System.currentTimeMillis();		
		encoder.encode(fileLocation, encryptedFileDestination, key);		
		System.out.println("encoding duration: " + (System.currentTimeMillis() - startTime));
		
		// about 5 times faster if executed after encoding, due to optimization
		startTime = System.currentTimeMillis();		
		encoder.decode(encryptedFileDestination, decryptedFileDestination, key);		
		System.out.println("decoding duration: " + (System.currentTimeMillis() - startTime));
	}
	
	private static LinkedList<Character> initCharKey() {
		LinkedList<Character> key = new LinkedList<Character>();

		for(int i = 1; i < 256; i++){
			key.add((char)i);
		}	
		
		key.add((char)0);
		return key;
	}
}
