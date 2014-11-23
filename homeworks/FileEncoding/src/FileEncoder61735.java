import java.io.*;
import java.util.LinkedList;

public class FileEncoder61735 implements FileEncoder {

   @java.lang.Override
   public void encode(String inFile, String outFile, LinkedList<Character> key)
         throws IOException {
      FileInputStream in = null;
      FileOutputStream out = null;

      try {
         in = new FileInputStream(inFile);
         out = new FileOutputStream(outFile);
         int c;
         long counter = 0;
         while ((c = in.read()) != -1) {
            if (isPrime(counter) || counter == 1) {
               out.write(c);
            } else {
               out.write(key.get(c));
            }
            counter++;
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (in != null) {
            in.close();
         }
         if (out != null) {
            out.close();
         }
      }
   }

   @java.lang.Override
   public void decode(String encodedFile, String outFile, LinkedList<Character> key)
         throws IOException {
      FileInputStream in = null;
      FileOutputStream out = null;

      try {
         in = new FileInputStream(encodedFile);
         out = new FileOutputStream(outFile);
         int c;
         long counter = 0;
         while ((c = in.read()) != -1) {
            if (isPrime(counter) || counter == 1) {
               out.write(c);
            } else {
               out.write(key.indexOf((char)c));
            }
            counter++;
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (in != null) {
            in.close();
         }
         if (out != null) {
            out.close();
         }
      }
   }

   private boolean isPrime(long number) {
      if (number == 0) {
         return false;
      }
      if (number == 2) {
         return true;
      }
      for (int i = 2; i * i < number; i++) {
         if (number % i == 0) {
            return false;
         }
      }
      return true;
   }
}
