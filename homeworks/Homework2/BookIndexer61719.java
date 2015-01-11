import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BookIndexer61719 implements IBookIndexer {

	int OFFSET = 97;

	@Override
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {
		Arrays.sort(keywords);
		SimpleList score[] = new SimpleList[keywords.length];
		TrieNode dictionary = new TrieNode();
		for (int i = 0; i < keywords.length; i++) {
			insertWord(dictionary, keywords[i], i);
			score[i] = new SimpleList();
		}
		try (FileReader fr = new FileReader(bookFilePath)) {
			reading(score, dictionary, fr);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		try (FileWriter write = new FileWriter(indexFilePath)) {
			write.write("INDEX");
			for (int i = 0; i < keywords.length; i++) {
				write.write("\r\n" + keywords[i] + score[i].toString());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	private void reading(SimpleList[] score, TrieNode dictionary, FileReader fr)
			throws IOException {
		int currentChar = 0;
		while (currentChar != -1) {
			int pageNum = readPageNum(fr);
			do {
				currentChar = fr.read();
				if (currentChar < OFFSET) {
					currentChar += 'a' - 'A';
				}
			} while (currentChar < OFFSET || currentChar > OFFSET + 25);
			readingLine(score, dictionary, fr, pageNum, currentChar);
			currentChar = fr.read();
		}
	}

	private void readingLine(SimpleList[] score, TrieNode dictionary,
			FileReader fr, int pageNum, int currentChar) throws IOException {
		do {
			TrieNode curNode = dictionary;
			while (currentChar != -1 && currentChar != 31
					&& (currentChar < OFFSET || currentChar > OFFSET + 25)) {
				currentChar = fr.read();
				if (currentChar < OFFSET) {
					if (currentChar == 10 || currentChar == 13) {
						return;
					}
					currentChar += 'a' - 'A';
				}
			}
			while (curNode != null && currentChar >= OFFSET
					&& currentChar < OFFSET + 26) {
				curNode = curNode.links[currentChar - OFFSET];
				currentChar = fr.read();
				if (currentChar < OFFSET) {
					currentChar += 'a' - 'A';
				}
			}
			if (curNode == null) {
				while (currentChar >= OFFSET && currentChar < OFFSET + 26) {
					currentChar = fr.read();
					if (currentChar < OFFSET) {
						currentChar += 'a' - 'A';
					}
				}
			}
			if (currentChar < OFFSET) {
				currentChar -= 'a' - 'A';
			}
			if (curNode != null && curNode.wordIndex > -1 && currentChar != -1) {
				score[curNode.wordIndex].add(pageNum);
			}
		} while (currentChar != -1 && currentChar != 10 && currentChar != 13);
	}

	private int readPageNum(FileReader fr) throws IOException {
		int result = 0;
		int ch;
		do {
			ch = fr.read();
		} while (ch != '=' && ch != -1);
		while (ch == '=') {
			ch = fr.read();
		}
		fr.skip(5);
		ch = fr.read();
		while (ch != ' ' && ch != -1) {
			result *= 10;
			result += ch - '0';
			ch = fr.read();
		}
		do {
			ch = fr.read();
		} while (ch == '=');
		return result;
	}

	private class TrieNode {
		TrieNode[] links;
		int wordIndex = -1;

		TrieNode() {
			links = new TrieNode[26];
		}
	}

	private void insertWord(TrieNode root, String word, int index) {
		int l = word.length();
		char[] letters = word.toCharArray();
		TrieNode curNode = root;

		for (int i = 0; i < l; i++) {
			if (curNode.links[letters[i] - OFFSET] == null) {
				curNode.links[letters[i] - OFFSET] = new TrieNode();// letters[i]);
			}
			curNode = curNode.links[letters[i] - OFFSET];
		}
		curNode.wordIndex = index;
	}

	public class SimpleList {
		// Data fields
		private StringBuilder toString = new StringBuilder();
		private int firstIndex = Integer.MAX_VALUE;
		private int lastIndex = Integer.MAX_VALUE;

		public void add(int e) {

			if (lastIndex == e) {
				return;
			}
			if (lastIndex == e - 1) {
				++lastIndex;

				return;
			}
			if (firstIndex == lastIndex) {
				if (firstIndex != Integer.MAX_VALUE) {
					toString.append(", ").append(firstIndex);
				}
				firstIndex = lastIndex = e;
				return;
			}

			toString.append(", ").append(firstIndex).append("-")
					.append(lastIndex);
			firstIndex = lastIndex = e;
		}

		public String toString() {
			if (firstIndex == lastIndex) {
				toString.append(", ").append(firstIndex);
			} else {
				toString.append(", ").append(firstIndex).append("-")
						.append(lastIndex);
			}
			return toString.toString();
		}
	}
}
