package hw1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.LinkedList;

public class FileEncoder61704 implements IFileEncoder61704{

	@Override
	public void encode(String sourceFile, String destinationFile, LinkedList<Character> key)
	{
		File destination = new File(destinationFile);
		try 
		{
			if(!destination.exists())
			{
				destination.createNewFile();
			}
			
			FileInputStream istream = new FileInputStream(sourceFile);
			BufferedInputStream bistream = new BufferedInputStream(istream);
			FileOutputStream ostream = new FileOutputStream(destinationFile);
			BufferedOutputStream bostream = new BufferedOutputStream(ostream);
		
			int counter = 0;
			int b = bistream.read();
			while(b != -1)
			{
				if(isPrime(counter))
				{
					bostream.write(b);
				}
				else
				{
					bostream.write(key.get(b));
				}
				counter++;
				b = bistream.read();	
			}
			
			bistream.close();
			bostream.close();
		} catch (FileNotFoundException e) {}
		 catch (IOException e) {}
	}

	@Override
	public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) 
	{
		File destination = new File(destinationFile);
		try 
		{
			if(!destination.exists())
			{
				destination.createNewFile();
			}
			
			FileInputStream istream = new FileInputStream(encodedFile);
			BufferedInputStream bistream = new BufferedInputStream(istream);
			FileOutputStream ostream = new FileOutputStream(destinationFile);
			BufferedOutputStream bostream = new BufferedOutputStream(ostream);
		
			int counter = 0;
			int b = bistream.read();
			while(b != -1)
			{
				if(isPrime(counter))
				{
					bostream.write(b);
				}
				else
				{
					bostream.write(key.indexOf((char)b));
				}
				counter++;
				b = bistream.read();	
			}
			
			bistream.close();
			bostream.close();
		} catch (FileNotFoundException e) {}
		 catch (IOException e) {}
	}

	 private static boolean isPrime(int n) {
         if (n <= 0) {
             return false;
         }
         if (n == 1 || n == 2) {
             return true;
         }
         for (int i = 2; i <= Math.sqrt(n) + 1; i++) {
             if (n % i == 0) {
                 return false;
             }
         }
         return true;
     }
}
