/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda_hw2_official;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author icho9_000
 */
public class BookIndexer61669 implements IBookIndexer {

    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        HashMap<String, String> lowercaseToOriginalKeywordHashMap = new HashMap<>();
        HashMap<String, ArrayList<Integer>> indexerHashMap = new HashMap<>();
        HashSet<String> lowercaseKeywordsHashSet = new HashSet<>();
        ArrayList<String> keywordsArrayList = new ArrayList<>();

        File file_out = new File(indexFilePath);

        Arrays.sort(keywords);

        for (String keyword : keywords) {
            String lowercaseKeyword = keyword.toLowerCase();
            lowercaseKeywordsHashSet.add(lowercaseKeyword);
            keywordsArrayList.add(keyword);
            lowercaseToOriginalKeywordHashMap.put(keyword, lowercaseKeyword);
        }

        try {
            FileOutputStream fout = new FileOutputStream(indexFilePath);
            if (!file_out.exists()) {
                file_out.createNewFile();
            }

            bufferedReader = new BufferedReader(new FileReader(bookFilePath));
            bufferedWriter = new BufferedWriter(new FileWriter(indexFilePath));

            bufferedWriter.write("INDEX");
            bufferedWriter.newLine();

            String line;
            int pageNum = 0;

            while ((line = bufferedReader.readLine()) != null) {
                int lineLength = line.length();
                if (lineLength >= 14 && line.substring(0, 8).equals("=== Page") && line.substring(lineLength - 4, lineLength).equals(" ===")) {
                    String str = line.substring(9, line.length() - 4);
                    pageNum = Integer.parseInt(str);
                } else {
                    String[] wordsInLine = line.split("\\s+");
                    String endCharWord = null;
                    String startCharWord = null;
                    String endChar = null;
                    String startChar = null;

                    for (String word : wordsInLine) {
                        word = word.toLowerCase();
                        if (word.length() != 0) {
                            endChar = word.substring(word.length() - 1);
                            startChar = word.substring(0, 1);

                            if (endChar.equals(".") || endChar.equals(",") || endChar.equals("!") || endChar.equals("?") || endChar.equals(":")
                                    || endChar.equals(";") || endChar.equals("'") || endChar.equals("\"")) {
                                word = word.substring(0, word.length() - 1);
                            }

                            if (startChar.equals(".") || startChar.equals(",") || startChar.equals("!") || startChar.equals("?") || startChar.equals(":")
                                    || startChar.equals(";") || startChar.equals("'") || startChar.equals("\"")) {
                                word = word.substring(1, word.length());
                            }
                        }

                        if (lowercaseKeywordsHashSet.contains(word)) {
                            ArrayList<Integer> arrayOfPages = indexerHashMap.get(word);

                            if (arrayOfPages == null) {
                                arrayOfPages = new ArrayList<Integer>();
                            }

                            if (!arrayOfPages.contains(pageNum)) {
                                arrayOfPages.add(pageNum);
                            }

                            indexerHashMap.put(word, arrayOfPages);
                       }
                    }
                }

            }
            
            for (String keyword : keywordsArrayList) {
                String str = lowercaseToOriginalKeywordHashMap.get(keyword);
                ArrayList<Integer> arrayOfPages = indexerHashMap.get(str);
                
                bufferedWriter.write(keyword);
                formatPageIndexOutput(arrayOfPages, bufferedWriter);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(BookIndexer61669.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    private void formatPageIndexOutput(ArrayList<Integer> arrayOfPages, BufferedWriter bufferedWriter) {
        int i = 0;
        boolean isDashed = false;
        int startIndex = 0, endIndex = 0;
        while (i < arrayOfPages.size()) {
            startIndex = arrayOfPages.get(i);
            isDashed = false;

            while (i < arrayOfPages.size() - 1 && arrayOfPages.get(i + 1) - arrayOfPages.get(i) == 1) {
                endIndex = arrayOfPages.get(i + 1);
                i++;
                isDashed = true;
            }
            i++;
            if (isDashed) {
                try {
                    String str = Integer.toString(startIndex) + "-" + Integer.toString(endIndex);
                    bufferedWriter.write(", " + str);
                } catch (IOException ex) {
                    Logger.getLogger(BookIndexer61669.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    bufferedWriter.write(", " + Integer.toString(startIndex));
                } catch (IOException ex) {
                    Logger.getLogger(BookIndexer61669.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
