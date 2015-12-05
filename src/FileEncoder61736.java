package fileencryption;

import java.util.LinkedList;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tonkata
 */
public class FileEncoder61736 implements FileEncoderFN {
    
    @Override
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key){
        ArrayList<Integer> content = new ArrayList<Integer>();
        try {
            InputStream input = new BufferedInputStream(new FileInputStream(sourceFile));
            for (int currentCharacter = input.read(); currentCharacter != -1; currentCharacter = input.read()) {
                content.add(currentCharacter);
            }
            input.close();
        } 
        catch (IOException exception) {
            System.out.println("Wrong file path: " + exception.getMessage());
        }

        int contentSize = content.size();
        int[] result = new int[contentSize];
        ArrayList<Character> keyArray = new ArrayList<Character>(key);
        int temp;
        for (int i = 0; i < contentSize; i++) {
            if (isPrime(i)) {
                result[i] = content.get(i);
            } 
            else {
                temp = content.get(i);
                result[i] = keyArray.get(temp);
            }
        }
        
         try {
            int size = result.length;
            OutputStream output = new BufferedOutputStream(new FileOutputStream(destinationFile));
            for (int i = 0; i < size; i++) {
                output.write(result[i]);
            }
            output.close();
        }
        catch (IOException exception) {
            System.out.println("IOException: " + exception.getMessage());
        }        
    }
    
    @Override
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
        ArrayList<Integer> content = new ArrayList<Integer>();
        try {
            
            InputStream input = new BufferedInputStream(new FileInputStream(encodedFile));
            for (int currentCharacter = input.read(); currentCharacter != -1; currentCharacter = input.read()) {
                content.add(currentCharacter);
            }
            input.close();
        } 
        catch (IOException exception) {
            System.out.println("Wrong file path: " + exception.getMessage());
        }
        
        int contentSize = content.size();
        int[] result = new int[contentSize];
        ArrayList<Integer> keyArray = new ArrayList<Integer>(key.size());
        LinkedList<Character> keyCopy = (LinkedList<Character>) key.clone();
        for (int i = 0; i < key.size(); i++) {
            int temp = keyCopy.pop();
            keyArray.add(temp);
        }

        for (int i = 0; i < contentSize; i++) {
            if (isPrime(i)) {
                result[i] = content.get(i);
            } 
            else {
                int a = content.get(i);
                int b = keyArray.get(a);
                result[i] = b;
                
            }
        }
        
         try {
            int size = result.length;
            OutputStream output = new BufferedOutputStream(new FileOutputStream(destinationFile));
            for (int i = 0; i < size; i++) {
                output.write(result[i]);
            }
            output.close();
        } catch (IOException exception) {
            System.out.println("IOException: " + exception.getMessage());
        }
    }
    
    public boolean isPrime(int number){
        if (number <= 3 && number > 0){
            return true;
        }
        if(number%2==0){
            return false;
        }
        for(int i = 3; i <= Math.sqrt(number); i+=2){
            if(number % i == 0){
                return false;
            }
        }

    return true;
    }
}
