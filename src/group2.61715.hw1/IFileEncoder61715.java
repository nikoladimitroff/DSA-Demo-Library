import java.io.IOException;
import java.util.LinkedList;

/**
 * You have to implement this class. The encryption algorithm is described below.
 * Replace FN with your faculty number.
 * @author Georgi Gaydarov
 */
public interface IFileEncoder61715 {
	 /**
     * Encodes a file with the specified key and saves the result to a given file.
     * @param sourceFile - path to the initial file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes
	 * @throws IOException 
     */
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) throws IOException;

    /**
     * Decodes a file that was encoded with the above algorithm.
     * @param encodedFile - path to encoded file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes that were used to encode the file
     * @throws IOException 
     */
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) throws IOException;
}
