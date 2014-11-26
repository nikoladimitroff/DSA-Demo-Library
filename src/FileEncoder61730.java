package fileencryption;

import java.util.LinkedList;
import java.io.*;
import java.util.*;

public class FileEncoder61730 implements FileEncoderFN {

    public ArrayList<Integer> readFile(String sourceFile) {
        try {
            ArrayList<Integer> fileContent = new ArrayList<Integer>();
            FileInputStream inputStream = new FileInputStream(sourceFile);
            BufferedInputStream inputFileStream = new BufferedInputStream(inputStream);
            for (int currentCharacter = inputFileStream.read(); currentCharacter != -1; currentCharacter = inputFileStream.read()) {
                fileContent.add(currentCharacter);
            }
            inputFileStream.close();
            return fileContent;
        } catch (IOException e) {
            System.out.println("Invalid file path - IOException caught: "
                    + e.getMessage());
        }
        return null;
    }

    public void writeFile(String destinationFile, int[] content) {
        try {
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            BufferedOutputStream outputFileStream = new BufferedOutputStream(outputStream);
            int size = content.length;
            for (int i = 0; i < size; ++i) {
                outputFileStream.write(content[i]);
            }
            outputFileStream.close();
        } catch (IOException e) {
            System.out.println("Caught IOException " + e.getMessage());
        }
    }

    public boolean isPrime(double number) {
        if (number <= 3 && number >= 1) {
            return true;
        } else if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        int i = 5;
        while (i < Math.sqrt(number) + 1) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        return true;
    }

    public void encode(String sourceFile, String destinationFile,LinkedList<Character> key) {
        ArrayList<Integer> content = readFile(sourceFile);
        int contentSize = content.size();
        int[] result = new int[contentSize];
        ArrayList<Character> key_array = new ArrayList<Character>(key);
        char c;
        int p;
        for (int i = 0; i < contentSize; i++) {
            if (isPrime(i)) {
                result[i] = content.get(i);
            } else {
                p = content.get(i);
                result[i] = key_array.get(p);
            }
        }
        writeFile(destinationFile, result);
    }

    public void decode(String encodedFile, String destinationFile,LinkedList<Character> key) {
        ArrayList<Integer> content = readFile(encodedFile);
        int contentSize = content.size();
        int[] result = new int[contentSize];
        ArrayList<Integer> key_array = new ArrayList<Integer>(key.size());
        LinkedList<Character> key_copy = (LinkedList<Character>) key.clone();
        for (int i = 0; i < key.size(); i++) {
            int c = key_copy.pop();
            key_array.add(c);
        }
        Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
        for (int i = 0; i < key.size(); i++) {
            ht.put(key_array.get(i), i);
        }
        for (int i = 0; i < contentSize; i++) {
            if (isPrime(i)) {
                result[i] = content.get(i);
            } else {
                int c = content.get(i);
                int p = ht.get(c);
                result[i] = p;
            }
        }
        writeFile(destinationFile, result);
    }
}
