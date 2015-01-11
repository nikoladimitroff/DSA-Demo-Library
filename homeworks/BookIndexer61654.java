/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bookindexer61654;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BookIndexer61654 implements IBookIndexer {

    private HashMap<String, HashSet<Integer>> results;
    String nonAlphaNumerical = "[^a-zA-Z0-9-]";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public BookIndexer61654() {
        results = new HashMap<String, HashSet<Integer>>();
    }

    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        createKeyWordsMap(keywords);
        try {
            initiateReadingProcedure(bookFilePath, keywords);
            generateIndex(keywords, indexFilePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void initiateReadingProcedure(String bookFilePath, String[] keywords) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(bookFilePath));
        String currentLine = "";
        Integer currentPage = -1;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith("=== Page ") && currentLine.endsWith(" ===")) {
                String currentPageStr = currentLine.substring(9, currentLine.length() - 4);
                try {
                    currentPage = new Integer(currentPageStr);
                } catch (Exception e) {
                }
            } else {
                String pageWords = currentLine.replaceAll(nonAlphaNumerical, " ");
                for (String keyword : keywords) {
                    if (pageWords.toUpperCase().contains(keyword.toUpperCase())) {
                        results.get(keyword.toUpperCase()).add(currentPage);
                    }
                }
            }
        }
    }

    private void generateIndex(String[] keywords, String indexFilePath) throws UnsupportedEncodingException, FileNotFoundException {
        Arrays.sort(keywords);
        StringBuilder index = new StringBuilder();
        index.append("INDEX");
        index.append(LINE_SEPARATOR);
        for (String keyword : keywords) {
            HashSet<Integer> set = results.get(keyword.toUpperCase());

            if (set.size() != 0) {
                List<Integer> sequence = new ArrayList<Integer>();
                Integer[] setArr = set.toArray(new Integer[0]);
                index.append(keyword);
                for (int i = 0; i < setArr.length; i++) {
                    if (i != setArr.length - 1 && setArr[i + 1] == setArr[i] + 1) {
                        sequence.add(setArr[i]);
                    } else if (sequence.size() != 0) {
                        sequence.add(setArr[i]);
                        index.append(", " + sequence.get(0) + "-" + sequence.get(sequence.size() - 1));
                        sequence = new ArrayList<Integer>();
                    } else {
                        index.append(", " + setArr[i]);
                    }
                }
                index.append(LINE_SEPARATOR);
            }
        }
        PrintStream out = new PrintStream(new FileOutputStream(indexFilePath, /* append */ false), true, "cp1251");
        out.print(index);
        out.close();
    }

    private void createKeyWordsMap(String[] keywords) {
        for (String keyword : keywords) {
            HashSet<Integer> set = new HashSet<Integer>();
            String upper = keyword.toUpperCase();
            results.put(upper, set);
        }
    }
}
