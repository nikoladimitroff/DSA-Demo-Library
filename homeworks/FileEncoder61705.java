package homework1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.imageio.stream.FileImageInputStream;

public class FileEncoder61705 implements FileEncoderFN{

	@Override
	public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
		try(BufferedInputStream reader = new BufferedInputStream(new FileInputStream(sourceFile));
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(destinationFile));){
			ArrayList<Integer> list = new ArrayList<>();//заради LinkedList<Character>
			int b = reader.read();
			while(b != -1){
				list.add(b);
				b = reader.read();
			}
			reader.close();
			System.out.println("Input" + Arrays.toString(list.toArray()));
			System.out.println("Key: " + Arrays.toString(key.toArray()));	// и тук да добавим един KeyInt (вероятност за гърменето: непълен ключ)
			System.out.println(list.get(0));
			writer.write((int)key.get(list.get(0)));
			writer.write(list.get(1));
			System.out.println("First: " + (int)key.get(list.get(0)));
			System.out.println("Second: " + (list.get(1)));
			for (int i = 2; i < list.size(); i++) {
				if(isPrime(i)){							// if i is a prime number or 1: write b to the destination file as is
					writer.write(list.get(i));
							System.out.println( (i+1) + ": " + (list.get(i)));
				}else{
					writer.write((int)key.get(list.get(i))); // write to the destination file the value of the b-th element of the key
							System.out.println((i+1) + ": " + (int)(key.get(list.get(i))));
				}
			}
			//Output would be 251 3 2 0 42
			writer.close();
		} catch (FileNotFoundException e) {}
		  catch (IOException e) {}
	}

	@Override
	public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
		try(BufferedInputStream reader = new BufferedInputStream(new FileInputStream(encodedFile));
				BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(destinationFile));){
				ArrayList<Integer> list = new ArrayList<>();//заради LinkedList<Character>
				int b = reader.read();
				while(b != -1){
					list.add(b);
					b = reader.read();
				}
				reader.close();
				
				ArrayList<Integer> keyInt = new ArrayList<>();
				for (int i = 0; i < key.size(); i++) {
					keyInt.add((int)key.get(i));
				}
				
				
//				System.out.println("list get 0: " + list.get(0));
//				System.out.println("index in key: " + key.indexOf(list.get(0)));
				writer.write(key.indexOf(list.get(0)));
				System.out.println("FIRST: " + keyInt.indexOf(list.get(0)));
				writer.write(list.get(1));
				System.out.println("Second: " + list.get(1));
				for (int i = 2; i < list.size(); i++) {
					if(isPrime(i)){							// if i is a prime number or 1: write b to the destination file as is
						writer.write(list.get(i));
						System.out.println(list.get(i));
					}else{
						writer.write(key.indexOf(list.get(i)));
						System.out.println(keyInt.indexOf(list.get(i)));
					}
				}
				writer.close();
			} catch (FileNotFoundException e) {}
			  catch (IOException e) {}
	}
	
//	public static boolean isPrime(int num) {
//	    int to = (int) Math.sqrt(num) + 1;
//	    boolean prim = true;
//	    for (int i = 2; i <= to; i++) {
//	        if (num % i == 0) {
//	            prim = false;
//	            break; //use of this 'break' increases the speed 15 times;
//	        }
//	    }
//	    return num > 1 && prim;
//	}

	public static boolean isPrime(int num) {
	    for (int i = 2; i < num; i++) {
	        if (num % i == 0) {
	           return false;
	        }
	    }
	    return true;
	}
}


/*You are given a path to a file which has to be encoded. 
 * You are also given a path where to write the resulting file. 
 * Finally, you are given a list of 256 bytes, each different from the others. 
 * Your task is to implement the encryption and decryption methods described above using the following algorithm:
 

For i = 0, i < sourceFile.lengthInBytes, i++
    char b = sourceFile[i]
    if i is a prime number or 1:
        write b to the destination file as is
    else:
        write to the destination file the value of the b-th element of the key
Here's an example. Let's say you are given the following source file and key:

Source file: 4 3 2 0 5...

Key: 123 101 111 222 251 42...

Then the output file should start like this:

Output: 251 3 2 0 42 ...

i = 0 is not a prime, we replace it with the 4th element of the key (write 251 to the output).
i = 1 is a special case and fits in the prime number category, we leave it as is (write 3 to the output)
i = 2 is a prime, we leave it as is (write 2 to the output)
i = 3 is a prime, leave it as is (write 0 to the output)
i = 4 is not a prime, replace it with 5th element of the key (write 42 to the output)
To put it another way:

The first, second, third, fifth, seventh, etc... byte in the input file is preserved. The fourth, sixth, eighth, ninth, etc. byte in the input file is encoded. Your class should be able to encode files in this manner and decode them accordingly.*/