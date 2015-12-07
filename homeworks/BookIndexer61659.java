import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class BookIndexer61659 implements IBookIndexer {

	private HashMap<String, ArrayList<Integer>> mHashMap = new HashMap<>();
	private HashMap<String, HashSet<Integer>> mFastContainingCheckMap = new HashMap<>();
	private HashSet<String> mLowercaseKeywordsSet = new HashSet<>();
	private HashMap<String, String> mLowercaseToOriginalMap = new HashMap<>();
	private ArrayList<String> mSortedKeywords = new ArrayList<>();

	@Override
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {

		for (String keyword : keywords) {
			String lowercase = keyword.toLowerCase();

			if (lowercase.endsWith(".")) {
				lowercase = lowercase.substring(0, lowercase.length() - 1);
			}

			mLowercaseKeywordsSet.add(lowercase);
			mLowercaseToOriginalMap.put(lowercase, keyword);
			mSortedKeywords.add(lowercase);
		}

		Collections.sort(mSortedKeywords);

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(bookFilePath));
			String line;
			int pageNumber = -1;

			while ((line = br.readLine()) != null) {
				if (line.startsWith("=== Page ") && line.endsWith(" ===")) {
					String substr = line.substring(9, line.length() - 4);
					int n = -1;
					try {
						n = Integer.parseInt(substr);
					} catch (NumberFormatException e) {
					}

					if (n != -1) {
						pageNumber = n;
					} else {
						analyzeLine(pageNumber, line, keywords);
					}
				} else if(line.length() > 0) {
					analyzeLine(pageNumber, line, keywords);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		writeToIndexFile(indexFilePath);
	}

	public void writeToIndexFile(String indexFilePath) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		StringBuilder sb = new StringBuilder();

		try {
			fos = new FileOutputStream(indexFilePath);
			osw = new OutputStreamWriter(fos);

			sb.append("INDEX\r\n");
			for (String keyword : mSortedKeywords) {
				String original = mLowercaseToOriginalMap.get(keyword);
				String formatted = formatResult(original, mHashMap.get(keyword));
				sb.append(formatted + "\r\n");
			}

			osw.write(sb.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String formatResult(String keyword, ArrayList<Integer> arrayList) {
		StringBuilder sb = new StringBuilder();
		sb.append(keyword);

		if (arrayList != null) {
			int n = arrayList.size();
			for (int i = 0; i < n; i++) {
				int beg = i;
				int end = i;
				if (i < n - 1) {
					while (arrayList.get(end + 1) - arrayList.get(end) == 1) {
						end++;
						if (end >= n - 1) {
							break;
						}
					}
				}

				if (beg == end) {
					sb.append(", " + arrayList.get(i));
				} else {
					sb.append(", " + arrayList.get(beg) + "-"
							+ arrayList.get(end));
					i = end;
				}
			}
		}

		return sb.toString();
	}

	private void analyzeLine(int pageNumber, String line, String[] keywords) {
		String[] wordsInLine = line.split("[\\W]+");
		for (String word : wordsInLine) {
			word = word.toLowerCase();

			if (mLowercaseKeywordsSet.contains(word)) {
				ArrayList<Integer> pages = mHashMap.get(word);
				HashSet<Integer> ints = mFastContainingCheckMap.get(word);

				if (pages == null) {
					pages = new ArrayList<Integer>();
					ints = new HashSet<>();
				}

				if (!ints.contains(pageNumber)) {
					pages.add(pageNumber);
					ints.add(pageNumber);
				}

				mHashMap.put(word, pages);
				mFastContainingCheckMap.put(word, ints);
			}
		}
	}

}
