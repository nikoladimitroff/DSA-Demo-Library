import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FileEncoder61659 implements FileEncoder {

	private final static int TYPE_ENCODE = 1;
	private final static int TYPE_DECODE = 2;

	@Override
	public void encode(String sourceFile, String destinationFile,
			LinkedList<Character> key) {
		perform(TYPE_ENCODE, sourceFile, destinationFile, key);
	}

	@Override
	public void decode(String encodedFile, String destinationFile,
			LinkedList<Character> key) {
		perform(TYPE_DECODE, encodedFile, destinationFile, key);
	}

	private void perform(int type, String src, String dest,
			LinkedList<Character> key) {

		File source = new File(src);
		File destination = new File(dest);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ArrayList<Character> orderedKeys = new ArrayList<Character>(key);
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i=0; i<key.size(); i++) {
			map.put(key.get(i), i);
		}

		try {
			fis = new FileInputStream(source);
			fos = new FileOutputStream(destination);

			int currentByte, i = 0;
			while ((currentByte = fis.read()) != -1) {

				int write = currentByte;
				if (i != 1 && !isPrime(i)) {
					if (type == TYPE_ENCODE) {
						write = orderedKeys.get(currentByte);
					} else if (type == TYPE_DECODE) {
						write = map.get((char) currentByte);
					}
				}

				fos.write(write);
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private boolean isPrime(int n) {

		if (n == 2) {
			return true;
		}

		if (n % 2 == 0) {
			return false;
		}

		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}
