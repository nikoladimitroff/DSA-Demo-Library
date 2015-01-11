import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileEncoder61715 implements IFileEncoder61715 {
    private static Boolean[] primals;
	
	@Override
	public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) throws IOException {
		byte[] keyToArray = StructureConverter.GetArrayFromLinkedList(key);
        code(destinationFile, sourceFile, keyToArray);		
	}

	@Override
	public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) throws IOException {
		byte[] keyToArray = StructureConverter.GetReversedArrayFromLinkedList(key);
        code(destinationFile, encodedFile, keyToArray);		
	}
	
	private static void code(String destinationFile, String sourceFile, byte[] keyToArray) throws IOException {
		byte[] sourcceFileToArray = StructureConverter.GetBytesFromFile(sourceFile);
		int elementsCount = sourcceFileToArray.length; // since they are no more than 300,000
		byte[] codedBytes = new byte[elementsCount];
		if(primals == null){
			initPrimals(elementsCount);
		}
		
        for (int i = 0; i < elementsCount; i++)
        {
            byte b = sourcceFileToArray[i];

            if (isPrime(i))
            {
                codedBytes[i] = b;
            }
            else
            {
                codedBytes[i] = keyToArray[(0 <= b && b <= 127)? b : 256 + (int)b];
            }
        }
        
        Files.write(Paths.get(destinationFile), codedBytes);
	}
    
	// saved for future :)
	private static void initPrimals(int size){
		  primals = new Boolean[size];
		  primals[0] = false;
		  primals[1] = true;
		  primals[2] = true;
	}
	
	// complexity of (sqrt(n)/2)
	private static Boolean isPrime(int number) {
	     if(primals[number] != null) {
	    	 return primals[number];
	     }
	      
	     if (number % 2 == 0) {
	    	 primals[number] = false;
	    	 
	         return false;
	     }
	      
	     int end = (int) Math.sqrt(number) + 1;
	     for (int i = 3; i < end; i += 2) {
	         if (number % i == 0) {
	        	 primals[number] = false;
	        	 
	             return false;
	         }
	     }
	
	     primals[number] = true;
	     
	     return true;
	}
}
