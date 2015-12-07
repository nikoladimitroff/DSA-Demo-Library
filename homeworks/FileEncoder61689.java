
package sda_first_homework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileEncoder61689 implements FileEncoderFN {
	@Override
    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
        File output_file = new File(destinationFile);
        FileOutputStream file_out = null;
        try {
            file_out = new FileOutputStream(output_file);
            if (!output_file.exists()) {
                output_file.createNewFile();
            }

            Path file_path = Paths.get(sourceFile);
            byte[] file_data = Files.readAllBytes(file_path);

            for (int i = 0; i < file_data.length; i++) {
                int n = file_data[i] & 0xff;
                if (isPrime(i)) {
                    file_out.write(n);
                } else {
                    file_out.write(key.get(n));
                }
            }
            file_out.flush();
            
        } catch (IOException exeption) {
            System.out.println(exeption);
        } finally {
        	if (file_out != null) {
        		try {             
                    	file_out.close();
        			} catch (IOException exeption) {
        				System.out.println(exeption);
        			}
        	}
        }
    }
	@Override
    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
        File output_file = new File(destinationFile);
        FileOutputStream file_out = null;
        try {
            file_out = new FileOutputStream(output_file);
            if (!output_file.exists()) {
                output_file.createNewFile();
            }
            
            Path file_path = Paths.get(encodedFile);
            byte[] file_data = Files.readAllBytes(file_path);

            for (int i = 0; i < file_data.length; i++) {
                int n = file_data[i] & 0xff;
                if (isPrime(i)) {
                    file_out.write(n);
                } else {
                    file_out.write(key.indexOf((char) n));
                }
            }
            file_out.flush();
        } catch (IOException exeption) {
            System.out.println(exeption);
        } finally {
        	if (file_out != null) {
        		try {
                    file_out.close();
        			}catch (IOException exeption) {
        				System.out.println(exeption);
        			}
        	}
        }
    }

    
     boolean isPrime(int number) {
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