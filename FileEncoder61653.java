package hw1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class FileEncoder61653 implements FileEncoderFN {

    @Override
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
       ArrayList<Integer> byteKey = new ArrayList<Integer>();
        for (char c : key) {
             byteKey.add((int) c);
        }
       
        try
        {
            File in = new File(sourceFile);
            FileInputStream inputStream = new FileInputStream(in);
            byte[] fileContent = new byte[(int)in.length()];
            inputStream.read(fileContent);
            inputStream.close();
            
            System.out.println("In" + Arrays.toString(fileContent));
            System.out.println("Key: " + Arrays.toString(byteKey.toArray()));
               
            File out = new File(destinationFile);  
            FileOutputStream outputStream = new FileOutputStream(out);
            for (int i = 0; i < fileContent.length; i++){
                int b = fileContent[i];
                if (isPrime(i)) {
                    outputStream.write(b);
                } else {
                    outputStream.write(byteKey.get(b & 0xff));
                }
            }
            outputStream.close();
        } 
          catch (FileNotFoundException e) {
            System.out.println("Not found" + e);
        }
        catch(IOException ioe)
        {
            System.out.println("Exception reading the file " + ioe);
        }
    }

    @Override
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
         ArrayList<Integer> byteKey = new ArrayList<Integer>();
        for (char c : key) {
             byteKey.add((int) c);
        }
       
        try
        {
            File in = new File(encodedFile);
            FileInputStream inputStream = new FileInputStream(in);
            byte[] fileContent = new byte[(int)in.length()];
            inputStream.read(fileContent);
            inputStream.close();
            
            System.out.println("Encoded File: " + Arrays.toString(fileContent));
            System.out.println("Key: " + Arrays.toString(byteKey.toArray()));
               
            File out = new File(destinationFile);  
            FileOutputStream outputStream = new FileOutputStream(out);
            for (int i = 0; i < fileContent.length; i++){
                int b = fileContent[i];
                if (isPrime(i)) {
                    outputStream.write(b);
                } else {
                    outputStream.write(byteKey.indexOf(b & 0xff));
                }
            }
            outputStream.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Not found" + e);
        }
        
        catch(IOException ioe)
        {
            System.out.println("Exception reading the file " + ioe);
        }
    }
    
    public static boolean isPrime(int number) {
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 3; i < sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    
}
