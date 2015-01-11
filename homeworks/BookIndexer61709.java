
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BookIndexer61709 implements IBookIndexer {
    public BookIndexer61709() {}

    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        HashMap<String, ArrayList<Integer> > index = new HashMap<String, ArrayList<Integer> >();
        
        for(String s : keywords) {
            index.put(s.toLowerCase(), new ArrayList<Integer>());
        }
        
        FileInputStream is;
        BufferedReader br;
        OutputStream os;
        BufferedWriter bw;
        
        try {
            is = new FileInputStream(bookFilePath);
            br = new BufferedReader(new InputStreamReader(is));
            os = new FileOutputStream(indexFilePath);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            
            int currentPage = -1;
            
            String line;
            ArrayList<Integer> current;
            while ( (line = br.readLine()) != null) {
                if (line.startsWith("=== Page ")) {
                    line = line.substring(9);
                    line = line.substring(0, line.indexOf(' '));
                    currentPage = Integer.parseInt(line);
                    continue;
                }
                
                line = line.toLowerCase();
                String word;
                int length = line.length();
                int beginIndex = -1;
                char c;
                boolean isValidChar;
                for(int i=0; i<length; ++i) {
                    c = line.charAt(i);
                    isValidChar = ((c <= 'z' && c >= 'a') || (c <= '9' && c >= '0') || c == '-');
                    if(beginIndex == -1 && isValidChar) {
                        beginIndex = i;
                    }
                    if(beginIndex != -1 && !isValidChar) {
                        word = line.substring(beginIndex, i);
                        beginIndex = -1;
                        if(index.containsKey(word)) {
                            current = index.get(word);
                            if(current.size() == 0 || current.get(current.size()-1) != currentPage) 
                                current.add(currentPage);
                        }
                    }
                }
                if(beginIndex != -1) {
                    word = line.substring(beginIndex);
                    if(index.containsKey(word)) {
                        current = index.get(word);
                        if(current.size() == 0 || current.get(current.size()-1) != currentPage) 
                            current.add(currentPage);
                    }
                }
            }
            
            Arrays.sort(keywords);
            
            bw.write("INDEX");
            bw.newLine();
            
            for(String s : keywords) {
                ArrayList<Integer> ts = index.get(s.toLowerCase());
                if(ts.isEmpty())
                    continue;
                bw.write(s);
                Integer prev = -1;
                boolean printed = false;
                
                for (Integer i : ts) {
                    if (prev == -1) {
                        bw.write(", ");
                        bw.write(i.toString());
                        prev = i;
                        printed = true;
                    } else if (prev == i-1) {
                        prev = i;
                        printed = false;
                    } else {
                        if (!printed) {
                            bw.write("-");
                            bw.write(prev.toString());
                        }
                        bw.write(", ");
                        bw.write(i.toString());
                        prev = i;
                        printed = true;
                    }
                }
                if (!printed) {
                    bw.write("-");
                    bw.write(prev.toString());
                }
                
                bw.newLine();
            }
            
            br.close();
            bw.flush();
            bw.close();
            is.close();
            os.close();
        } catch (Exception ex) {}
    }
}
