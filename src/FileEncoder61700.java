package homework1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class FileEncoder61700 implements FileEncoderFN {

	private static boolean[] isPrimeArray;

	public FileEncoder61700() {
		init();
	}

	@Override
	public void encode(String sourceFile, String destinationFile,
			LinkedList<Character> key) {
		// for faster indexing
		ArrayList<Character> myKey = new ArrayList<Character>(key);
		File inputFile = new File(sourceFile);
		if (!inputFile.exists() || inputFile.isDirectory()) {
			System.out.println("File does not exist or is directory");
			return;
		}

		try {
			FileInputStream inputStream = new FileInputStream(sourceFile);
			FileOutputStream outputStream = new FileOutputStream(destinationFile);
			BufferedInputStream inputFileStream = new BufferedInputStream(inputStream);
			BufferedOutputStream outputFileStream = new BufferedOutputStream(outputStream);
			// would really prefer this not to be a loop
			// but to read and write the whole thing as a batch
			for (int i = 0, currentCharacter = inputFileStream.read(); currentCharacter != -1; ++i, currentCharacter = inputFileStream.read()) {
				if (isPrime(i)) {
					outputFileStream.write(currentCharacter);
				} else {
					outputFileStream.write(myKey.get(currentCharacter));
				}
			}
			inputFileStream.close();
			outputFileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decode(String encodedFile, String destinationFile,
			LinkedList<Character> key) {
		// for faster search
		Character[] myKey = new Character[256]; // 256 - the key's length
		Iterator<Character> it = key.listIterator();
		for (int index = 0; it.hasNext(); index++) {
			char currentChar = it.next();
			myKey[index] = currentChar;
		}

		try {
			FileInputStream inputStream = new FileInputStream(encodedFile);
			FileOutputStream outputStream = new FileOutputStream(destinationFile);
			BufferedInputStream inputFileStream = new BufferedInputStream(inputStream);
			BufferedOutputStream outputFileStream = new BufferedOutputStream(outputStream);
			// would really prefer this not to be a loop
			// but to read and write the whole thing as a batch
			for (int i = 0, currentCharacter = inputFileStream.read(); currentCharacter != -1; ++i, currentCharacter = inputFileStream.read()) {
				if (isPrime(i)) {
					outputFileStream.write(currentCharacter);
				} else {
					outputFileStream.write(myKey[currentCharacter]);
				}
			}
			inputFileStream.close();
			outputFileStream.close();
		} catch (Exception ex) {
			System.err.println("An exception occured while writing :(");
		}
	}

	private boolean isPrime(int num) {
		return isPrimeArray[num];
	}

	private void init() {
		int N = 307200; // 300 kb at most
		isPrimeArray = new boolean[N + 1];
		isPrimeArray[1] = true;
		for (int i = 2; i <= N; i++) {
			isPrimeArray[i] = true;
		}

		for (int i = 2; i * i <= N; i++) {

			if (isPrimeArray[i]) {
				for (int j = i; i * j <= N; j++) {
					isPrimeArray[i * j] = false;
				}
			}
		}
	}

}
