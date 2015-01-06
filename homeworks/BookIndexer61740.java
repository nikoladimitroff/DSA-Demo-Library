package com.fmi.sda.bookindexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class BookIndexer61740 implements IBookIndexer {
	
	private final static int PAGE_NUMBER_BEGINNING_LENGTH = 9;
	private final static int PAGE_NUMBER_ENDING_LENGTH = 4;
	private ArrayList<String> lowercaseKeywords = new ArrayList<String>();
	private HashMap<String, String> originalLowerKWMap = new HashMap<String, String>();
	private HashMap<String, HashSet<Integer>> keywordsPagesMap = new HashMap<String, HashSet<Integer>>();
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {
		
		init(keywords);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(bookFilePath));
			String line;
			int pageNumber = 0;
			while((line = reader.readLine()) != null) {
				if(line.startsWith("=== Page ") && line.endsWith(" ===")){
					pageNumber = Integer.parseInt(line.substring(PAGE_NUMBER_BEGINNING_LENGTH,line.length() - PAGE_NUMBER_ENDING_LENGTH));
				} else {
					parseLine(line, pageNumber);
				}
			}
			String content = "INDEX";
			for(String keyword: lowercaseKeywords) {
				content = formatContent(content, keyword);
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(indexFilePath)));
			writer.write(content);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private String formatContent(String content, String keyword) {
		content += "\r\n";
		content += originalLowerKWMap.get(keyword);
		int n = keywordsPagesMap.get(keyword).size();
		ArrayList<Integer> pages = new ArrayList<Integer>(keywordsPagesMap.get(keyword));
		for (int i = 0; i < n; i++) {
			int start = i;
			int end = i;
			if (i < n - 1) {
				while (pages.get(end + 1) - pages.get(end) == 1) {
					end++;
					if (end >= n - 1) {
						break;
					}
				}
			}
			
			if (start == end) {
				content += ", " + pages.get(i);
			} else {
				content += ", " + pages.get(start) + "-" + pages.get(end);
				i = end;
			}
		}
		return content;
	}
	private void parseLine(String line, int pageNumber) {
		String[] words = line.split("[^a-zA-Z0-9-]+");
		for(String word: words) {
			String lower = word.toLowerCase();
			if(lowercaseKeywords.contains(lower)) {
				if(!keywordsPagesMap.containsKey(lower)) {
					keywordsPagesMap.put(lower, new HashSet<Integer>());
				}
				keywordsPagesMap.get(lower).add(pageNumber);
			}
		}
	}
	private void init(String[] keywords) {
		for(String keyword: keywords) {
			String lowercaseKW = keyword.toLowerCase();
			lowercaseKeywords.add(lowercaseKW);
			originalLowerKWMap.put(lowercaseKW, keyword);
		}
		Collections.sort(lowercaseKeywords);
	}

}
