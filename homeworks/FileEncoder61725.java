import java.util.ArrayList;
import java.util.LinkedList;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 *@author Simeon Tashev
 *
 */
public class FileEncoder61725 {
		private ArrayList<Boolean> eratoList = new ArrayList<Boolean>();
		
		FileEncoder61725()
		{
			for (int i = 0; i < 250000; i++) {
				eratoList.add(true);
		    }
			
            eratoList.set(0, false);
			eratoList.set(1,true);
			
			for (int i = 2; i < Math.sqrt(250000); i++) {
				
				if(eratoList.get(i)==true)
				{	
					for (int j = i; j * i < 250000; j++) 
					{
						eratoList.set(i*j, false);
				    }
				}
			}		
		}
		public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) throws IOException
		{
			InputStream in = new FileInputStream(sourceFile);
			OutputStream out = new FileOutputStream(destinationFile);

			int counter = 0;
			int was_read = 0;
			while((was_read  = in.read())!=-1)
			{
				if(!isPrime(counter))
				{
					out.write(key.get(was_read));
				}
				else
				{
					out.write(was_read);
				}
				counter++;
			}
			in.close();
			out.close();
		}
		private boolean isPrime(int number) {
			if(eratoList.get(number))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		public void decode(String sourceFile, String destinationFile, LinkedList<Character> key) throws IOException
		{
			InputStream in = new FileInputStream(sourceFile);
			OutputStream out = new FileOutputStream(destinationFile);
			
			int counter = 0;
			int was_read = 0;
			while((was_read=in.read())!=-1) 
			{
				if(!isPrime(counter))
				{
					out.write((char)key.indexOf(was_read));
				}
				else
				{
					out.write(was_read);
				}
				counter++;
			}
			in.close();
			out.close();
		}
}
