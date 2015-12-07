package com.praimgo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FileEncoder61670 implements FileEncoderFN {

	private ArrayList<Long> listOfPrimes;
	private boolean[] primesBoolean;

	public FileEncoder61670() {
		generatePrimes();
	}

	private void generatePrimes() {
		SoEInfHashMap sieve = new SoEInfHashMap();
		Long x = sieve.next();
		listOfPrimes = new ArrayList<Long>();
		listOfPrimes.add(1l);
		listOfPrimes.add(2l);
		primesBoolean = new boolean[300010];
		Arrays.fill(primesBoolean, Boolean.FALSE);

		while (sieve.hasNext() && x < 300000) {
			x = sieve.next();
			listOfPrimes.add(x);
		}
		for (long l : listOfPrimes) {
			primesBoolean[safeLongToInt(l)] = true;
		}
	}

	private int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	@Override
	public void encode(String sourceFile, String destinationFile,
			LinkedList<Character> key) {
		List<Character> myKey = new ArrayList<Character>(key);
		Path path = Paths.get(sourceFile);
		try {
			byte[] data = Files.readAllBytes(path);
			for (Long l : listOfPrimes) {
				if (l > data.length) {
					break;
				}
				int i = safeLongToInt(l);
				int t = data[i] & 0xff;
				int x = Integer.valueOf(myKey.get(t).charValue());
				data[i] = (byte) (x);
			}
			Files.write(Paths.get(destinationFile), data,
					StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decode(String encodedFile, String destinationFile,
			LinkedList<Character> key) {
		List<Character> myKey = new ArrayList<Character>(key);
		Path path = Paths.get(encodedFile);
		try {
			byte[] data = Files.readAllBytes(path);
			for (Long l : listOfPrimes) {
				if (l > data.length) {
					break;
				}
				int i = safeLongToInt(l);
				int t = data[i] & 0xff;
				int x = Integer.valueOf(myKey.get(t).charValue());
				data[i] = (byte) (x);
			}
			Files.write(Paths.get(destinationFile), data,
					StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class SoEInfHashMap implements Iterator<Long> {

		long candidate = 2;
		Iterator<Long> baseprimes = null;
		long basep = 3;
		long basepsqr = 9;
		final HashMap<Long, Long> nonprimes = new HashMap<>();

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Long next() {
			if (this.candidate <= 5L)
				if (this.candidate++ == 2L)
					return 2L;
				else {
					this.candidate++;
					if (this.candidate == 5L)
						return 3L;
					else {
						this.baseprimes = new SoEInfHashMap();
						this.baseprimes.next();
						this.baseprimes.next(); // throw away 2 and 3
						return 5L;
					}
				}
			for (; this.candidate >= this.basepsqr || // equals nextbase squared
														// => not prime
					nonprimes.containsKey(this.candidate); candidate += 2) {
				if (candidate >= basepsqr) { // if square of base prime, always
												// equal
					long adv = this.basep << 1;
					nonprimes.put(this.basep * this.basep + adv, adv);
					this.basep = this.baseprimes.next();
					this.basepsqr = this.basep * this.basep;
				}
				else {
					long adv = nonprimes.remove(this.candidate);
					long nxt = this.candidate + adv;
					while (this.nonprimes.containsKey(nxt))
						nxt += adv; // unique keys
					this.nonprimes.put(nxt, adv);
				}
			}
			long tmp = candidate;
			this.candidate += 2;
			return tmp;
		}
	}
}
