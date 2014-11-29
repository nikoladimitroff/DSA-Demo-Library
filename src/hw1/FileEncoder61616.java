package hw1;

import java.io.*;
import java.util.LinkedList;

/**
 * File Encoder
 * 
 * @author  Martin Grozdanov <megrozdanov@gmail.com>
 */
public class FileEncoder61616 implements IFileEncoder61616
{
    /**
     * Encodes a file with the specified key and saves the result to a given file.
     * 
     * @param String                sourceFile      path to the initial file
     * @param String                destinationFile path to the result file
     * @param LinkedList<Character> key             list of replacement bytes
     * @return void
     */
    @Override public void encode(String sourceFile, String destinationFile, LinkedList<Character> key)
    {
        _process(sourceFile, destinationFile, key);
    }

    /**
     * Decodes a file that was encoded with IFileEncoder61616::encode
     * 
     * @param String                sourceFile      path to the initial file
     * @param String                destinationFile path to the result file
     * @param LinkedList<Character> key             list of replacement bytes
     * @return void
     */
    @Override public void decode(String sourceFile, String destinationFile, LinkedList<Character> key)
    {
        _process(sourceFile, destinationFile, key);
    }

    /**
     * Encodes/Decodes a file that was decoded/encoded with this method
     * 
     * @param String                sourceFile      path to the initial file
     * @param String                destinationFile path to the result file
     * @param LinkedList<Character> key             list of replacement bytes
     * @return void
     */
    protected void _process(String sourceFile, String destinationFile, LinkedList<Character> key)
    {
        try {
            File hDestinationFile = new File(destinationFile);

            if (hDestinationFile.exists()) {
                hDestinationFile.createNewFile();
            }

            BufferedInputStream  bsIn  = new BufferedInputStream(new FileInputStream(sourceFile));
            BufferedOutputStream bsOut = new BufferedOutputStream(new FileOutputStream(destinationFile));

            int counter = 0;
            int srcByte = bsIn.read();

            while(srcByte != -1) {
                if(counter == 1 || isPrime(counter)) {
                    bsOut.write(srcByte);
                } else {
                    bsOut.write((int) key.get(srcByte));
                }
                counter++;
                srcByte = bsIn.read();
            }

            bsIn.close();
            bsOut.close();

        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println("Something went wrong.");
        }
    }

    private static boolean isPrime(int num)
    {
        if (num != 2 && (num < 2 || num % 2 == 0)) return false;

        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;

        return true;
    }
}