
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileEncoder61714 implements FileEncoderFN {

    private static boolean[] primeNumbers;

    public FileEncoder61714() {
        this.getPrimes();
    }

    private void getPrimes() {
        int maxLength = 307200;
        primeNumbers = new boolean[maxLength + 1];
        primeNumbers[0] = false;
        primeNumbers[1] = true;
        for (int i = 2; i <= maxLength; i++) {
            primeNumbers[i] = isPrimeNumber(i);
        }
    }

    private boolean isPrimeNumber(int n) {
        if (n == 0) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private boolean isPrime(int n) {
        return primeNumbers[n];
    }

    public void encode(String inFile, String outFile, LinkedList<Character> key) {
        BufferedInputStream inputStream;
        BufferedOutputStream outputStream;

        ArrayList<Character> newKey = new ArrayList<>(key);

        try {
            inputStream = new BufferedInputStream(new FileInputStream(inFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(outFile));

            int currentByte;
            int index = 0;
            while ((currentByte = inputStream.read()) > -1) {

                if (this.isPrime(index)) {
                    outputStream.write(currentByte);
                } else {
                    outputStream.write(newKey.get(currentByte));
                }
                index++;
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            System.err.println("Error occurred during encoding file");
        }
    }

    public void decode(String encodedFile, String outFile, LinkedList<Character> key) {
        BufferedInputStream inputStream;
        BufferedOutputStream outputStream;

        ArrayList<Character> newKey = new ArrayList<>(key);
        try {
            inputStream = new BufferedInputStream(new FileInputStream(encodedFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(outFile));

            int currentByte;
            int index = 0;
            while ((currentByte = inputStream.read()) > -1) {

                if (this.isPrime(index)) {
                    outputStream.write(currentByte);
                } else {
                    outputStream.write(newKey.indexOf((char) currentByte));
                }
                index++;
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            System.err.println("Error occurred during decoding file");
        }
    }
}
