
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import javax.naming.ldap.HasControls;



public class FileEncoder61701 implements FileEncoderFN {
		
	public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {		
		this.doReadEncodeWrite(sourceFile, destinationFile, key);
	}
	public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {		
		this.doReadEncodeWrite(encodedFile, destinationFile, key);
	}
	
	private void doReadEncodeWrite(String inputFilename, String outputFilename, LinkedList<Character> key) {
		InputStream inputStream;
		OutputStream output;
		int counter = 0;
		
		try {
			inputStream = new FileInputStream(inputFilename);
			output = new FileOutputStream(outputFilename);
			
			int data;
			data = inputStream.read();
			while(data != -1) {
				int current = this.isPrime(counter) ? data : (int)key.get(data); 
				
				//System.out.println(data + " -> " + current + " ");
				
				output.write(current);
				data = inputStream.read();
				counter++;
			}
			
			inputStream.close();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isPrime(int number) {
		if (number == 0)
			return false;
		if (number == 1)
			return true;
		
		double numberRoot = Math.sqrt(number);
		for(int i = 2; i <= numberRoot; i++){
            if(number % i == 0){
                return false;
            }
        }
		return true;
		
	}
	
}
