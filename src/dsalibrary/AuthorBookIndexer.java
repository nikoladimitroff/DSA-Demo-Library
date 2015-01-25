package dsalibrary;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nikola
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;


class Pair {
    public int first;
    public int second;
    
    public Pair(int page) {
        this.first = this.second = page;
    }
    
    @Override
    public String toString() {
        if (this.first == this.second)
            return String.valueOf(this.first);
        return String.format("%d-%d", this.first, this.second);
    }
}

public class AuthorBookIndexer implements IBookIndexer {
    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        String[] sortedKeys = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            sortedKeys[i] = keywords[i].toLowerCase();
        }
        Arrays.sort(sortedKeys);
        HashMap<String, ArrayList<Pair>> keywordLocations = new HashMap<>();
        for (String keyword : sortedKeys) {
            keywordLocations.put(keyword, new ArrayList<>());
        }
        try {
            Scanner scanner = new Scanner(new File(bookFilePath));
            scanner.useDelimiter("[^a-zA-Z0-9=-]");
            int currentPage = 0;

            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (word.equals("===")) {
                    String marker = scanner.findInLine("Page \\d+ ===");
                    if (marker != null) {
                        String pageNumber = marker.replace("Page ", "").replace(" ===", "");
                        currentPage = Integer.parseInt(pageNumber);
                        continue;
                    }
                }
                if (keywordLocations.containsKey(word)) {
                    ArrayList<Pair> locations = keywordLocations.get(word);
                    boolean shouldAddNewPage = locations.isEmpty() ||
                        locations.get(locations.size() - 1).second < currentPage - 1;
                    if (shouldAddNewPage) {
                        locations.add(new Pair(currentPage));
                    }
                    else {
                        locations.get(locations.size() - 1).second = currentPage;
                    }
                }
            }
            this.writeIndex(sortedKeys, keywordLocations, indexFilePath);
            scanner.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void writeIndex(String[] keywords,
                            HashMap<String, ArrayList<Pair>> keywordLocations,
                            String indexFilePath) {
        try {
            PrintWriter writer = new PrintWriter(indexFilePath);
            writer.write("INDEX\n");
            for (int i = 0; i < keywords.length; i++) {
                String keyword = keywords[i];
                writer.write(keyword + ", ");
                ArrayList<Pair> locations = keywordLocations.get(keyword);
                String locationsString = locations.stream()
                        .map(Pair::toString)
                        .collect(Collectors.joining(", "));
                
                writer.write(locationsString);
                if (i != keywords.length - 1)
                    writer.write('\n');
            }
            writer.flush();
            writer.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
