import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class FileEncoder61685 {
	
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
    	List<Character> keys = new ArrayList<Character>(key);
    	Path path = Paths.get(sourceFile);
    	try {
		byte[] data = Files.readAllBytes(path);
		for(int i = 0; i < data.length; i++) {
			if(!isPrime(i)) {
				int t = data[i] & 0xff;
				data[i] = (byte) ((int)keys.get(t));
			}
		}
		Files.write(Paths.get(destinationFile), data, StandardOpenOption.CREATE);
	} catch (IOException e) {
		e.printStackTrace();
	}
    }

    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
    	List<Character> keys = new ArrayList<Character>(key);

    	Path path = Paths.get(encodedFile);
    	
    	try {
		byte[] data = Files.readAllBytes(path);

		for(int i = 0; i < data.length; i++)
		{
			if(!isPrime(i))
			{
				int t = data[i] & 0xff;
				data[i] = (byte) ((int)keys.indexOf((char) t));
			}
		}
		
		Files.write(Paths.get(destinationFile), data, StandardOpenOption.CREATE);
    	} catch (IOException e) {
		e.printStackTrace();
	}
    }
    
    private boolean isPrime(int number) {
    	if (number == 0) return false;
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}


