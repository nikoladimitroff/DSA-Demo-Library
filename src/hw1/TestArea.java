package hw1;

import java.util.LinkedList;
import java.io.*;

/**
 * TestArea
 * 
 * @author  Martin Grozdanov <megrozdanov@gmail.com>
 */
public class TestArea
{
    /**
     * Main function
     * 
     * @param args
     * @return void
     */
    public static void main(String[] args)
    {
        testEncode();
        testDecode();
    }

    /**
     * Test Encode
     *
     * @return void
     */
    protected static void testEncode()
    {
        try {
            File file = new File("toEncode.bin");
            // file.deleteOnExit();

            FileOutputStream fsOut = new FileOutputStream(file);
            fsOut.write(4);
            fsOut.write(3);
            fsOut.write(2);
            fsOut.write(0);
            fsOut.write(5);
            fsOut.close();

            FileEncoder61616 fileEncoder = new FileEncoder61616();

            LinkedList<Character> key = new LinkedList<>();

            key.add((char) 123);
            key.add((char) 4);
            key.add((char) 111);
            key.add((char) 5);
            key.add((char) 1);
            key.add((char) 3);

            fileEncoder.encode(file.getPath(), "encoded.bin", key);
            System.out.println("Encoding test: Done. See encoded.bin");

        } catch (IOException e) {
            System.err.println("Something went wrong.");
        }
    }

    /**
     * Test Decode
     *
     * @return void
     */
    protected static void testDecode()
    {
        try {
            File file = new File("toDecode.bin");
            // file.deleteOnExit();

            FileOutputStream fsOut = new FileOutputStream(file);
            fsOut.write(1);
            fsOut.write(3);
            fsOut.write(2);
            fsOut.write(0);
            fsOut.write(3);
            fsOut.close();

            FileEncoder61616 fileEncoder = new FileEncoder61616();

            LinkedList<Character> key = new LinkedList<>();

            key.add((char) 123);
            key.add((char) 4);
            key.add((char) 111);
            key.add((char) 5);
            key.add((char) 1);
            key.add((char) 3);

            fileEncoder.decode(file.getPath(), "decoded.bin", key);
            System.out.println("Decoding test: Done. See decoded.bin");

        } catch (IOException e) {
            System.err.println("Something went wrong.");
        }
    }
}