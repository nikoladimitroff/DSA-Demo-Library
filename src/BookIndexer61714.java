import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Arrays;

public class BookIndexer61714 implements IBookIndexer {

    private HashMap<String, TreeSet<Integer>> keywordPages;

    public BookIndexer61714() {
        this.keywordPages = new HashMap<String, TreeSet<Integer>>();
    }

    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toLowerCase();
            this.keywordPages.put(keywords[i], new TreeSet<Integer>());
        }
        Arrays.sort(keywords);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(bookFilePath));

            int currentPage = 0;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {

                if(currentLine.equals("")){
                    continue;
                }

                if(currentLine.trim().matches("=== Page \\d+ ===")){
                   currentPage = Integer.parseInt(currentLine.split(" ")[2]);
                }
                else{
                    String[] wordsInLine = currentLine.trim().split("[^=A-Za-z0-9-]");
                    for (String currentWord : wordsInLine) {
                        currentWord = currentWord.toLowerCase();
                        if (currentWord.equals("")) {
                            continue;
                        }
                        if (this.keywordPages.containsKey(currentWord)) {
                            this.keywordPages.get(currentWord).add(currentPage);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            StringBuilder output = new StringBuilder();
            output.append("INDEX");
            for (String currentWord : keywords) {
                TreeSet<Integer> pages = this.keywordPages.get(currentWord.toLowerCase());
                if (pages.size() != 0) {
                    output.append(System.lineSeparator())
                            .append(currentWord);
                    int prevPage = pages.first();
                    Boolean isFirst = true;
                    Boolean isConsecutive = false;
                    for (int page : pages) {
                        if (prevPage == page - 1) {
                            isConsecutive = true;

                            if (isFirst) {
                                output.append("-");
                                isFirst = false;
                            }
                        } else {
                            if (isConsecutive) {
                                output.append(prevPage);
                                isConsecutive = false;
                                isFirst = true;
                            }

                            output.append(", ").append(page);
                        }

                        prevPage = page;
                    }

                    if (isConsecutive) {
                        output.append(prevPage);
                    }
                }
            }
            PrintWriter out = null;
            try {
                out = new PrintWriter(indexFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            out.print(output.toString());
            out.close();
        }
    }
}

