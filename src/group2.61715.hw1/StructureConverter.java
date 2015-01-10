import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class StructureConverter {
	public static byte[] GetBytesFromFile(String fileLocation) throws IOException
	{
	    return Files.readAllBytes(Paths.get(fileLocation));
	}
	
	 public static byte[] GetArrayFromLinkedList(LinkedList<Character> linkedList)
	 {
	     int index = 0;
	     byte[] bytesToArray = new byte[256];
	
	     for (char item : linkedList)
	     {
	         bytesToArray[index] = (byte) item;
	         index++;
	     }
	
	     return bytesToArray;
	 }
	 
	 public static byte[] GetReversedArrayFromLinkedList(LinkedList<Character> linkedList)
     {
		 int index = 0;
		 byte[] reversedArray = new byte[256];

         for (char item : linkedList)
         {
             reversedArray[item] = (byte) index;
             index++;
         }

         return reversedArray;
     }
}
