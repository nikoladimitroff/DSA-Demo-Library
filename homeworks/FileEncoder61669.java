/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd1_hw1_official;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author icho9_000
 */

public class FileEncoder61669 implements FileEncoderFN {

    @Override
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
        File file_out = new File(destinationFile);
        FileOutputStream fout = null;
        ArrayList<Character> newKey = new ArrayList<>(key);
        try {
            fout = new FileOutputStream(file_out);
            if (!file_out.exists()) {
                file_out.createNewFile();
            }

            Path path = Paths.get(sourceFile);
            byte[] data = Files.readAllBytes(path);

            for (int i = 0; i < data.length; i++) {
                int num = data[i] & 0xff;
                if (isPrime(i)) {
                    fout.write(num);
                } else {
                    fout.write(newKey.get(num));
                }
            }
            fout.flush();
        } catch (IOException ex) {
            Logger.getLogger(Sd1_hw1_official.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    @Override
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
        File file_out = new File(destinationFile);
        FileOutputStream fout = null;
        ArrayList<Character> newKey = new ArrayList<>(key);
        try {
            fout = new FileOutputStream(file_out);
            if (!file_out.exists()) {
                file_out.createNewFile();
            }
            Path path = Paths.get(encodedFile);
            byte[] data = Files.readAllBytes(path);
            
            for (int i = 0; i < data.length; i++) {
                int num = data[i] & 0xff;
                if (isPrime(i)) {
                    fout.write(num);
                } else {
                    fout.write(newKey.indexOf((char) num));
                }
            }
            fout.flush();
        } catch (IOException ex) {
            Logger.getLogger(Sd1_hw1_official.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    boolean isPrime(int number) {
        if (number == 0) {
            return false;
        }
        
        if (number == 2 || number == 1) {
            return true;
        }
        
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
