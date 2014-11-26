package hw1;

import java.util.LinkedList;

/**
 * You have to implement this class. The encryption algorithm is described below.
 * Replace FN with your faculty number.
 * @author Georgi Gaydarov
 */
public interface FileEncoderFN { 
    /**
     * Encodes a file with the specified key and saves the result to a given file.
     * @param sourceFile - path to the initial file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes
     */
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key);

    /**
     * Decodes a file that was encoded with the above algorithm.
     * @param encodedFile - path to encoded file
     * @param destinationFile - path to the result file
     * @param key - list of replacement bytes that were used to encode the file
     */
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key);
}