import java.io.IOException;
import java.util.LinkedList;

/**
 * You have to implement this class. The encryption algorithm is described in the TaskOne.txt file in this repo.
 * 
 * @author Georgi Gaydarov
 *
 */
public interface FileEncoder {
	/**
	 * Encodes a file with a specified key and saves the result to a given path.
	 * @param inFile - path to the initial file
	 * @param outFile - path to the result file
	 * @param key - list of replacement bytes
	 */
	public void encode(String inFile, String outFile, LinkedList<Character> key) throws IOException;
	
	/**
	 * Decodes a file that was encoded with a specified key and saves the result to a given path.
	 * @param encodedFile - path to encoded file
	 * @param outFile - path to the result file
	 * @param key - list of replacement bytes that were used to encode the file
	 */
	public void decode(String encodedFile, String outFile, LinkedList<Character> key)
         throws IOException;
}
