package com.fmi.fileencoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class FileEncoder61740 implements FileEncoderFN {

	public void encode(String sourceFile, String destinationFile,
			LinkedList<Character> key) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(sourceFile);
			out = new FileOutputStream(destinationFile);
			int currentChar = in.read();
			int i = 0;
			while(currentChar != -1) {
				if(isPrime(i) || i == 1) {
					out.write(currentChar);
				} else {
					out.write(key.get(currentChar));
				}
				currentChar = in.read();
				i++;
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void decode(String encodedFile, String destinationFile,
			LinkedList<Character> key) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(encodedFile);
			out = new FileOutputStream(destinationFile);
			int currentChar = in.read();
			int i = 0;
			while(currentChar != -1) {
				if(isPrime(i) || i == 1) {
					out.write(currentChar);
				} else {
					out.write(key.indexOf(currentChar));
				}
				currentChar = in.read();
				i++;
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isPrime(int n) {
		if (n == 0) return false;
		if (n == 2) return true;
	    if (n%2 == 0) return false;
	    for(int i = 3;i * i <= n; i += 2) {
	        if(n%i == 0) return false;
	    }
	    return true;
	}
}
