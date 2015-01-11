package bookindexer;
import java.io.*;

import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Tonkata
 */
public class BookIndexer61736 implements IBookIndexer {
    private HashMap<String, String> hm = new HashMap<>();
    @Override
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
        
        for (String keyword : keywords) {
            String lowercase = keyword.toLowerCase();
            hm.put(lowercase, "");
        }
        
        Map<String, String> map = new TreeMap<String, String>(hm);
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(bookFilePath));
            String line;
            String pageNumber = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("=== Page ") && line.endsWith(" ===")) {
                    String [] splittedPageTitle = line.split(" ");
                    pageNumber = splittedPageTitle[2];
                }
                else{
                    String[] splittedLine = line.split(" ");
                    for (int i = 0; i < keywords.length; i++) {
                        String keywordPages = "";
                        if(!hm.get(keywords[i]).equals("")){
                            keywordPages += (hm.get(keywords[i]).toString());
                        }
                        for(String item: splittedLine){
                            if(keywords[i].equals(item.toLowerCase())){
                                keywordPages += (", "+pageNumber);
                                keywordPages.toString();
                                break;
                                }
                        }
                        hm.put(keywords[i], keywordPages);
                    }
                    
                }
            }
            
            for (HashMap.Entry<String, String> entry : hm.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.print(key +" ");
                System.out.print(value);
                System.out.println();
                
            }
        } catch (IOException exception) {
            System.out.println("Invalid file path: " + exception.getMessage());
        }
//        
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(indexFilePath));
            bw.write("INDEX");
            String output = "";
            for (HashMap.Entry<String, String> entry : hm.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                output = (key+""+value);
                bw.newLine();
                bw.write(output);
            }
            bw.close();
        } catch (IOException exception) {
            System.out.println("Caught IOException " + exception.getMessage());
        }
    }
}
