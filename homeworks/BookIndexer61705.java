package homework2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class BookIndexer61705 implements IBookIndexer{
	private Hashtable<String, String> keyWordsTable;
	 @Override
	    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
		 	keyWordsTable = this.copyKeyArrayIntoHashTable(keywords);
		 	this.countringKeyWords(bookFilePath, keywords);
		 	this.generatingOutputFile(indexFilePath, keywords);
		 	keyWordsTable = null;
	    }
	 
	 private Hashtable<String, String> copyKeyArrayIntoHashTable(String[] arr){
		 Hashtable hashTable = new Hashtable<String, String>();
	        for (int i = 0; i < arr.length; i++) {
	        	hashTable.put(arr[i].toLowerCase(), arr[i].toLowerCase() + " ");
	        }
	     return hashTable;
	 }

	 private void countringKeyWords(String bookFilePath, String[] keywords){
		 try (BufferedReader br = new BufferedReader(new FileReader(bookFilePath))) {
			 	String line = br.readLine();
	            while(line != null) {
	                String[] titleParts = line.split(" ");          
	                int pageNumber = Integer.parseInt(titleParts[2]);
	                line = br.readLine();
	                if(line == null) return;
	                for (int i = 0; i < keywords.length; i++) {
	                   // Pattern pattern = Pattern.compile("\\W" + keywords[i] + "\\W|^" + keywords[i] + "\\W|^" + keywords[i] + "$|\\W" + keywords[i] + "$");
	                    line = line.toLowerCase();
	                    if (line.contains(keywords[i]))
	                        keyWordsTable.put(keywords[i], keyWordsTable.get(keywords[i]).concat(", " + pageNumber));
	                }
	                line = br.readLine();
	                line = br.readLine();
	            }
	        } catch (IOException e) { System.out.println("Invalid file path" + e.getMessage()); }
	 }

	 private void generatingOutputFile(String indexFilePath, String[] keywords){
		 String[] outputArr = this.transformingOutput(keywords); 
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexFilePath))) {
	            bw.write("INDEX");
	            for (int i = 0; i < outputArr.length; i++) {
					bw.newLine();
	            	bw.write(outputArr[i]);
				}
        } catch (IOException e) {
            System.out.println("Some IOException has occured" + e.getMessage());
        }
	 }
	 
	 private String[] transformingOutput(String[] keywords){
		 String[] outputArray = new String[keyWordsTable.size()];
		 for (int i = 0; i < keywords.length; i++) {
              if (!(keywords[i] + " ").equalsIgnoreCase(keyWordsTable.get(keywords[i]))) {
                  String str = keyWordsTable.get(keywords[i]);
                  String[] tokens = str.split("[ ,]+");
                  				System.out.println(i+ " : " + Arrays.toString(tokens));
                  String output = tokens[0] + ", " + tokens[1];
                  for (int j = 2; j < tokens.length - 1; j++) {
                      if (Integer.parseInt(tokens[j]) - 1 == Integer.parseInt(tokens[j - 1])) {
                          if ( Integer.parseInt(tokens[j]) + 1 == Integer.parseInt(tokens[j + 1]))
                        	  output += ("-" + tokens[j+1]); // май трябва да има цикъл
                           else
                              output += ("-" + tokens[j]);
                      	 } 
                       else 
                          output += (", " + tokens[j]);
                  }
                  if(tokens.length != 2){
	                  if (tokens.length - 2 != 0 && Integer.parseInt(tokens[tokens.length - 1]) - 1 == Integer.parseInt(tokens[tokens.length - 2])) {
	                      output += ("-" + tokens[tokens.length - 1]);
	                  } else {
	                      output += (", " + tokens[tokens.length - 1]);
	                  }
                  }
                  outputArray[i] = output;
              }
          }
		 	Arrays.sort(outputArray);
		 	return outputArray;
	 }
}
