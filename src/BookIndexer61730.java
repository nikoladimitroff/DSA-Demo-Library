package indextable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Nikolai
 */
public class BookIndexer61730 implements IBookIndexer {

    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        String[] keywords1 = Arrays.copyOf(keywords, keywords.length);
        Hashtable ht = new Hashtable();
        for (int i = 0; i < keywords1.length; i++) {
            ht.put(keywords1[i].toLowerCase(), keywords1[i] + " ");
        }
        for (int i = 0; i < keywords1.length; i++) {
            keywords1[i] = keywords1[i].toLowerCase();
        }
        Arrays.sort(keywords1);
        //Reading the book and remembering keyword's pages
        try (BufferedReader br = new BufferedReader(new FileReader(bookFilePath))) {
            int pageNumber = 0;
            for (String line; (line = br.readLine()) != null;) {
                //----------------------------------
                if (line.contains("=== Page ")) {
                    String[] parts = line.split(" ");
                    pageNumber = Integer.parseInt(parts[2]);
                    //br.readLine();
                }
                for (int i = 0; i < keywords1.length; i++) {
                    Pattern pattern = Pattern.compile("\\W" + keywords1[i] + "\\W|^" + keywords1[i] + "\\W|^" + keywords1[i] + "$|\\W" + keywords1[i] + "$");
                    if (pattern.matcher(line.toLowerCase()).find()) {
                        String keywordPages = ht.get(keywords1[i]).toString();
                        keywordPages += (", " + pageNumber);
                        ht.put(keywords1[i], keywordPages);
                    }
                }
                //-----------------------------------
            }
        } catch (IOException e) {
            System.out.println("Invalid file path - IOException caught: " + e.getMessage());
        }
        //Writing the Index
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexFilePath))) {
            bw.write("INDEX");
            for (int i = 0; i < keywords1.length; i++) {
                if (!(keywords1[i] + " ").equals(ht.get(keywords1[i]).toString().toLowerCase())) {
                    String str = (ht.get(keywords1[i])).toString();
                    String delims = "[ ,]+";
                    String[] tokens = str.split(delims);
                    String output = tokens[0] + ", " + tokens[1];
                    for (int j = 2; j < tokens.length - 1; j++) {
                        if (Integer.parseInt(tokens[j]) - 1 == Integer.parseInt(tokens[j - 1])) {
                            if (Integer.parseInt(tokens[j]) + 1 == Integer.parseInt(tokens[j + 1])) {

                            } else {
                                output += ("-" + tokens[j]);
                            }
                        } else {
                            output += (", " + tokens[j]);
                        }
                    }
                    if (tokens.length > 2) {
                        if (Integer.parseInt(tokens[tokens.length - 1]) - 1 == Integer.parseInt(tokens[tokens.length - 2])) {
                            output += ("-" + tokens[tokens.length - 1]);
                        } else {
                            output += (", " + tokens[tokens.length - 1]);
                        }
                    }
                    bw.newLine();
                    bw.write(output);

                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Caught IOException " + e.getMessage());
        }
    }
}
