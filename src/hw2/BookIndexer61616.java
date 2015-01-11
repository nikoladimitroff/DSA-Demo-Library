package hw2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookIndexer61616 {
    /**
      * Builds the index of the specified book, containing the given keywords
      * and writes it to the given output file
      * 
      * @param bookFilePath - path to book file
      * @param keywords - an array of keywords
      * @param indexFilePath - path to the output index file
      * @author Martin Grozdanov <megrozdanov@gmail.com>
      */
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath)
    {
        /*
            The keywords are allowed to contain only alphabetical characters
            and the symbols dash (-) and underscore (_) .
         */
        try {
            /* Prepare keywords and index map */
            Map<String, LinkedList<Integer>> index = new HashMap<String, LinkedList<Integer>>();
            Arrays.sort(keywords);

            for (int i=0; i<keywords.length; i++) {
                keywords[i] = keywords[i].toLowerCase();
                index.put(keywords[i], new LinkedList<Integer>());
            }

            BufferedReader brIn = new BufferedReader(new FileReader(bookFilePath));

            int currentPage = -1;
            String line;
            Pattern newPageRegex = Pattern.compile("^=== Page (\\d+) ===$");
            Pattern wordRegex;
            Matcher matcher;

            /* Generate the index map */
            while ((line = brIn.readLine()) != null) {
                if (line.isEmpty()) continue; // skip empty lines

                matcher = newPageRegex.matcher(line);
                // Match new page
                if (matcher.find()) {
                    currentPage = Integer.parseInt(matcher.group(1));
                    continue;
                }

                if (currentPage != -1) {
                    for (int i=0; i<keywords.length; i++) {
                        wordRegex = Pattern.compile("([^-_\\w]|^)" + keywords[i] + "([^-_\\w]|$)", Pattern.CASE_INSENSITIVE);
                        if (wordRegex.matcher(line).find()) {
                            LinkedList<Integer> pages = index.get(keywords[i]);
                            pages.push(currentPage);
                        }
                    }
                }
            }

            brIn.close();

            /* Write the index */
            FileWriter fwOut = new FileWriter(indexFilePath);

            fwOut.write("INDEX");

            for (int i=0; i<keywords.length; i++) {
                LinkedList<Integer> pagesList = index.get(keywords[i]);

                Integer[] pages = pagesList.toArray(new Integer[pagesList.size()]);

                fwOut.write("\n" + keywords[i]);

                if (pages.length == 0)
                    continue;

                int pageRangeStart = pages[pages.length - 1];
                int pageRangeEnd   = pageRangeStart;

                for (int pI = pages.length -1 ; pI >= 0 ; pI--) {
                    int page = pages[pI];
                    if (pageRangeEnd == page-1)
                        pageRangeEnd = page;

                    if (pI == 0 || pageRangeEnd != page) {
                        if (pageRangeStart == pageRangeEnd) {
                            fwOut.write(", " + pageRangeStart);
                        } else {
                            fwOut.write(", " + pageRangeStart + "-" + pageRangeEnd);
                        }

                        if (pI == 0 && pageRangeEnd != page) {
                            fwOut.write(", " + page);
                        }

                        pageRangeStart = page;
                        pageRangeEnd = page;
                    }
                }
            }
            fwOut.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println("Something went wrong.");
        }
    }
}