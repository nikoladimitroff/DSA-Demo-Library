import java.io.*;
import java.util.LinkedList;

public class FileEncoder61735 implements FileEncoderFN {

   @java.lang.Override
   public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
      FileInputStream in = null;
      FileOutputStream out = null;

      try {
         in = new FileInputStream(sourceFile);
         out = new FileOutputStream(destinationFile);
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
            try {
               in.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         if (out != null) {
            try {
               out.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   @java.lang.Override
   public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
      FileInputStream in = null;
      FileOutputStream out = null;

      try {
         in = new FileInputStream(encodedFile);
         out = new FileOutputStream(destinationFile);
         int c;
         long counter = 0;
         while ((c = in.read()) != -1) {
            if (isPrime(counter) || counter == 1) {
               out.write(c);
            } else {
               out.write(key.indexOf((char) c));
            }
            counter++;
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally      {
         if (in != null) {
            try {
               in.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         if (out != null) {
            try {
               out.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }

   private boolean isPrime(long number) {
      if (number == 0 || number == 1) {
         return false;
      }
      if (number == 2) {
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
