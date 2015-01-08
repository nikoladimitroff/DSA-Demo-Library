import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BookIndexer61664 {
    private WordTree wordTree;

    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        Arrays.sort(keywords);
        ArrayList<Integer>[] pages = new ArrayList[keywords.length];
        wordTree = new WordTree();
        for (int i = 0; i < keywords.length; i++) {
            pages[i] = new ArrayList<Integer>();
            wordTree.addWord(keywords[i], i);
        }

        try {
            Scanner scanner = new Scanner(Paths.get(bookFilePath));
            scanner.useDelimiter("[^a-zA-Z0-9=-]");

            int currentPage = -1;
            while (scanner.hasNext()) {
                String word = scanner.next();

                if (word.equals("===")) {
                    word = scanner.next();
                    if (word.equals("Page")) {
                        word = scanner.next();
                        currentPage = Integer.valueOf(word);
                        scanner.next();
                        continue;
                    }
                }

                int kwIndex = wordTree.contains(word.toLowerCase());
                if (kwIndex != -1) {
                    pages[kwIndex].add(currentPage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(indexFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("INDEX");

        for (int i = 0; i < keywords.length; i++) {
            int st = -1, end = -1;
            writer.print("\n" + keywords[i]);

            for (int j = 0; j < pages[i].size(); j++) {
                if (st == -1) {
                    st = pages[i].get(j);
                    end = st;
                } else {
                    if (pages[i].get(j) - end <= 1)
                        end = pages[i].get(j);
                    else {
                        if (st == end)
                            writer.print(", " + st);
                        else
                            writer.print(", " + st + "-" + end);

                        st = pages[i].get(j);
                        end = st;
                    }
                }
            }

            if (st == end)
                writer.print(", " + st);
            else
                writer.print(", " + st + "-" + end);
        }

        writer.flush();
    }

    public class WordTree {
        Node base;

        public WordTree() {
            base = new Node();
        }

        public void addWord(String word, int wIndex) {
            Node currentNode = base;
            for (int i = 0; i < word.length(); i++) {
                if (currentNode.children.containsKey(word.charAt(i)))
                    currentNode = currentNode.children.get(word.charAt(i));
                else {
                    Node newNode = new Node();
                    currentNode.children.put(word.charAt(i), newNode);
                    currentNode = newNode;
                }
            }

            currentNode.wIndex = wIndex;
        }

        public int contains(String word) {
            Node currentNode = base;

            for (int i = 0; i < word.length(); i++) {
                currentNode = currentNode.children.get(word.charAt(i));
                if (currentNode == null)
                    return -1;
            }

            return currentNode.wIndex;
        }

        private class Node {
            int wIndex;

            HashMap<Character, Node> children;

            public Node() {
                wIndex = -1;
                children = new HashMap<Character, Node>();
            }
        }
    }
}
