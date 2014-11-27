import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Presko
 */
public class FileEncoder61654 implements FileEncoderFN {

    HashMap<Integer, Integer> primeNumbers = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> keyNumbers = new HashMap<Integer, Integer>();

    public FileEncoder61654() {
        createPrimeNumbersHash();
    }

    public void encode(String sourceFile, String destinationFile, LinkedList<Character> key) {
        createKeyNumbersHash(key);
        try {
            FileInputStream is = new FileInputStream(sourceFile);
            FileOutputStream os = new FileOutputStream(destinationFile);
            try {
                int nextByte = is.read();
                int index = 0;
                int encodedByte = 0;
                while (nextByte != -1) {
                    encodedByte = encodeByte(nextByte, index);
                    os.write(encodedByte);
                    nextByte = is.read();
                    ++index;
                }
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(FileEncoder61654.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileEncoder61654.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void decode(String encodedFile, String destinationFile, LinkedList<Character> key) {
        invertNumberKeyHash(key);
        try {
            FileInputStream is = new FileInputStream(encodedFile);
            FileOutputStream os = new FileOutputStream(destinationFile);
            try {
                int nextByte = is.read();
                int index = 0;
                while (nextByte != -1) {
                    nextByte = encodeByte(nextByte, index);
                    os.write(nextByte);
                    nextByte = is.read();
                    ++index;
                }
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(FileEncoder61654.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileEncoder61654.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int encodeByte(int value, int index) {
        Integer intObj = new Integer(index);
        if (primeNumbers.containsKey(intObj)) {
            return value;
        } else {
            Integer valueObj = new Integer(value);
            return keyNumbers.get(valueObj);
        }
    }

    private void createKeyNumbersHash(LinkedList<Character> key) {
        int i = 0;
        for (Character item : key) {
            keyNumbers.put(i++, (int) item);
        }
    }

    private void invertNumberKeyHash(LinkedList<Character> key) {
        int i = 0;
        for (Character item : key) {
            keyNumbers.put((int) item, i++);
        }
    }

    private void createPrimeNumbersHash() {
        boolean[] numbers = new boolean[300000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = true;
        }

        int max = (int) Math.sqrt(numbers.length);

        for (int i = 2; i <= max; i++) {
            if (numbers[i]) {
                for (int j = i * i; j < numbers.length; j += i) {
                    numbers[j] = false;
                }
            }
        }
        primeNumbers.put(1, 1);
        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i]) {
                primeNumbers.put(i, i);
            }
        }
    }
}
