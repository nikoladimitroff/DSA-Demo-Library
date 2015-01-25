package hw1;

import java.util.LinkedList;

/**
 * File Encoder Interface
 * 
 * @author  Martin Grozdanov <megrozdanov@gmail.com>
 */
public interface IFileEncoder61616
{ 
    /**
     * Encodes a file with the specified key and saves the result to a given file.
     * 
     * @param String                sourceFile      path to the initial file
     * @param String                destinationFile path to the result file
     * @param LinkedList<Character> key             list of replacement bytes
     * @return void
     */
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key);

    /**
     * Decodes a file that was encoded with IFileEncoder61616::encode
     * 
     * @param String                sourceFile      path to the initial file
     * @param String                destinationFile path to the result file
     * @param LinkedList<Character> key             list of replacement bytes
     * @return void
     */
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key);
}