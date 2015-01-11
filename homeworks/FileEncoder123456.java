import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Приемаме че списъка с ключове има 256 елемента.Тъй като в условието се казва
 * че им 256 различни байта. Тъй като char в java е 16 бит-a това означава че ще
 * се използват само последните 8 бита от всеки char.
 * 
 * Важно !. В условието не е казано но ключ с 256 уникални елемента може да кодира единствено 256 символа.
 * Това означава че Алогиритъма няма да работи с кодировки над 1 байт. Ще работи (ASCII,Extended ASCII и windows-1252).
 * но няма как с 8 битов ключ да се кодира UTF-8 текст което е голям недостатък.
 *
 */

public class FileEncoder123456 implements FileEncoderFN {

	@Override
	public void encode(String sourceFilePath, String destinationFilePath,
			LinkedList<Character> key) {

		/*
		 * Проверяваме дали някой от аргументите не е празен или null
		 */
		if (sourceFilePath == null) {
			throw new IllegalArgumentException(
					"Source File Path shouldn't be null");
		}
		if (sourceFilePath.isEmpty()) {
			throw new IllegalArgumentException(
					"Source File Path shouldn't be null");
		}

		if (destinationFilePath == null) {
			throw new IllegalArgumentException(
					"Destination File Path shouldn't be null");
		}
		if (destinationFilePath.isEmpty()) {
			throw new IllegalArgumentException(
					"Destination File Path shouldn't be null");
		}
		if (key == null) {
			throw new IllegalArgumentException(
					"The encoding key shouldn't be null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException(
					"The encoding key shouldn't be empty");
		}

		/*
		 * Преобразуваме списъка в масив от елементи char защото взимането на
		 * елемент от свързан списък по стойност е много бавно O(n) Докато при
		 * масив е 0(1)
		 */
		char[] keyArray = new char[256];
		for (int i = 0; i < 256; i++) {
			keyArray[i] = key.poll();
		}
		RandomAccessFile sourceFile = null;
		RandomAccessFile destFile = null;
		try {
			File sourceFileRegular = new File(sourceFilePath);
			if (!sourceFileRegular.exists()) {
				throw new IOException("Source File doesn't exist");
			}
			/*
			 * Ако има dest файл го трием и създаваме нов
			 */
			File desFileRegular = new File(destinationFilePath);
			if (desFileRegular.exists()) {
				desFileRegular.delete();
				desFileRegular.createNewFile();
			}
			sourceFile = new RandomAccessFile(sourceFilePath, "r");
			destFile = new RandomAccessFile(destinationFilePath, "rw");

			/*
			 * Обхождаме файла по байтове но използваме char за контейнер за
			 * удобство защото ключовете са char ако е просто число просто го
			 * записваме в dest файла, ако ли не го кодираме с съответния ключ
			 * 
			 * Важно. Този алгоритъм ще работи само до 256 символа, (което значи
			 * ASCII, Extended ASCII и windows-1252 но не и UTF-8) !
			 */
			for (int i = 0; i < sourceFile.length(); i++) {
				char currentCharacter = (char) sourceFile.read();
				if (isPrimeNumber(i)) {
					destFile.writeByte(currentCharacter);
				} else {
					destFile.writeByte(keyArray[currentCharacter]);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sourceFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				destFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void decode(String encodedFilePath, String destinationFilePath,
			LinkedList<Character> key) {

		/*
		 * Проверяваме дали някой от аргументите не е празен или null
		 */
		
		if (encodedFilePath == null) {
			throw new IllegalArgumentException(
					"Encoded File Path shouldn't be null");
		}
		if (encodedFilePath.isEmpty()) {
			throw new IllegalArgumentException(
					"Encoded File Path shouldn't be null");
		}

		if (destinationFilePath == null) {
			throw new IllegalArgumentException(
					"Destination File Path shouldn't be null");
		}
		if (destinationFilePath.isEmpty()) {
			throw new IllegalArgumentException(
					"Destination File Path shouldn't be null");
		}
		if (key == null) {
			throw new IllegalArgumentException(
					"The encoding key shouldn't be null");
		}
		if (key.isEmpty()) {
			throw new IllegalArgumentException(
					"The encoding key shouldn't be empty");
		}

		/*
		 * Тук прави точно обратното на encode метода. Ние имаме кодиран текст при който
		 * нормалните букви (a,b,c,d) са заменени с 8 бит ключ от списъка кат a„e„—Akè
		 * В случая за да върнем нормалните букви създаваме Хеш  Карта, с ключове
		 * кодираните букви  a„e„—Akè и стойности на всички букви до 256-стия символ в UTF(char-а се представя в JAVA с UTF-16).
		 * Т.е. правим Хеш Карта a„e„—Akè --> (a,b,c,d)
		 */
		HashMap<Character, Character> valueKey = new HashMap<Character, Character>();
		char current = '\0';
		for (Integer i = 0; i < 256; i++) {
			valueKey.put(key.poll(), current);
			current++;
		}
		RandomAccessFile encodedFile = null;
		RandomAccessFile destinationFile = null;
		try {
			File encodedFileRegular = new File(encodedFilePath);
			if (!encodedFileRegular.exists()) {
				throw new IOException("Source File doesn't exist");
			}
			File desFileRegular = new File(destinationFilePath);
			if (desFileRegular.exists()) {
				desFileRegular.delete();
				desFileRegular.createNewFile();
			}

			encodedFile = new RandomAccessFile(encodedFilePath, "r");
			destinationFile = new RandomAccessFile(destinationFilePath, "rw");
			/*
			 * След като имаме Хеш Картата 
			 * само заменяме всеки срещнат ключ с истинската буква.
			 *  a„e„—Akè --> (a,b,c,d)
			 */
			for (int i = 0; i < encodedFile.length(); i++) {
				char currentCharacter = (char) encodedFile.read();
				if (isPrimeNumber(i)) {
					destinationFile.writeByte(currentCharacter);
				} else {

					destinationFile.write(valueKey.get(currentCharacter));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				encodedFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				destinationFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static boolean isPrimeNumber(int number) {
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
