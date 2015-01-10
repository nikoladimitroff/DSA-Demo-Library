package homework2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class BookIndexer61700 implements IBookIndexer {
	private final String PageLineStart = "=== Page ";
	private final String PageLineEnd = " ===";
	private final int PageLineStartLength = this.PageLineStart.length();
	private final int PageLineEndLength = this.PageLineEnd.length();

	private HashMap<String, TreeSet<Integer>> keywordsPagesMapWithLowerCaseKeys = new HashMap<String, TreeSet<Integer>>();
	private HashMap<String, Pattern> keywordsPatternsMap = new HashMap<String, Pattern>();
	private Set<String> keywordsToLowerCase = new HashSet<String>();
	private SortedSet<String> sortedKeywords = new TreeSet<String>();

	@Override
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {
		String wordToLowerCase = null;
		for (String word : keywords) {
			this.sortedKeywords.add(word);
			wordToLowerCase = word.toLowerCase();
			this.keywordsPagesMapWithLowerCaseKeys.put(wordToLowerCase,
					new TreeSet<Integer>());
			this.keywordsPatternsMap.put(wordToLowerCase,
					Pattern.compile(".*\\b" + wordToLowerCase + "\\b.*"));
			this.keywordsToLowerCase = this.keywordsPagesMapWithLowerCaseKeys
					.keySet();
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(bookFilePath));
			String line;
			int currentPageNumber = 0;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(this.PageLineStart)
						&& line.endsWith(this.PageLineEnd)) {
					String pageNumber = line.substring(
							this.PageLineStartLength, line.length()
									- this.PageLineEndLength);
					currentPageNumber = Integer.parseInt(pageNumber);
				} else {
					// Bottleneck
					// line = line.toLowerCase();
					// for (String word : this.keywordsToLowerCase) {
					// if (this.keywordsPatternsMap.get(word).matcher(line)
					// .matches()) {
					// this.keywordsPagesMapWithLowerCaseKeys.get(word)
					// .add(currentPageNumber);
					// }
					// }
					// -----------

					// Bottleneck fix attempt
					String[] wordsInLine = line.split("[^a-zA-Z0-9-]+");
					for (String wordInLine : wordsInLine) {
						String wordInLineToLowerCase = wordInLine.toLowerCase();
						if (this.keywordsToLowerCase
								.contains(wordInLineToLowerCase)) {
							this.keywordsPagesMapWithLowerCaseKeys.get(
									wordInLineToLowerCase).add(
									currentPageNumber);
						}
					}
					// -----------
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// Save data to file
			StringBuilder result = new StringBuilder();
			result.append("INDEX");
			for (String word : this.sortedKeywords) {

				Iterator<Integer> iterator = this.keywordsPagesMapWithLowerCaseKeys
						.get(word.toLowerCase()).iterator();
				Boolean hasNext = false;
				if (iterator.hasNext()) {
					result.append("\r\n" + word + ", ");
					int initialPage, endPage, nextPage = 0;
					do {
						initialPage = nextPage == 0 ? (int) iterator.next()
								: nextPage;
						endPage = initialPage;
						nextPage = iterator.hasNext() ? (int) iterator.next()
								: 0;
						while (nextPage == endPage + 1) {
							endPage = nextPage;
							nextPage = iterator.hasNext() ? (int) iterator
									.next() : 0;
						}

						if (endPage == initialPage) {
							// Hasn't entered the while loop
							result.append(initialPage);
						} else {
							// Has entered the while loop
							result.append(initialPage + "-" + endPage);
						}

						hasNext = iterator.hasNext();
						if (hasNext) {
							result.append(", ");
						}

						if (!hasNext && nextPage != 0) {
							result.append(", " + nextPage);
						}
					} while (hasNext);
				}
			}

			FileOutputStream outputStream = null;
			OutputStreamWriter streamWriter = null;

			try {
				outputStream = new FileOutputStream(indexFilePath);
				streamWriter = new OutputStreamWriter(outputStream);
				streamWriter.write(result.toString());

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					streamWriter.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
