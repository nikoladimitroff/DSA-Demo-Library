package homework2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BookIndexer61705 implements IBookIndexer{
	private HashMap<String, String> keyWordsTable;
	 @Override
	    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
		 	keyWordsTable = this.copyKeyArrayIntoHashMap(keywords);
		 	this.countringKeyWords(bookFilePath, keywords);
		 	this.generatingOutputFile(indexFilePath, keywords);
		 	keyWordsTable = null;
		 	System.out.println("OK");
	    }
	 
	 private HashMap<String, String> copyKeyArrayIntoHashMap(String[] arr){
		 HashMap<String, String> hashMap = new HashMap<String, String>();
	        for (int i = 0; i < arr.length; i++) {
	        	hashMap.put(arr[i].toLowerCase(), arr[i].toLowerCase() + " ");
	        }
	     return hashMap;
	 }

	 private void countringKeyWords(String bookFilePath, String[] keywords){
		 try (BufferedReader br = new BufferedReader(new FileReader(bookFilePath))) {
			 	String line = br.readLine();
			 	int pageNumber = -1;
	            while(line != null) {
	            	if(line.contains("=== Page ")){
	            		String[] titleParts = line.split(" ");          
	            		pageNumber = Integer.parseInt(titleParts[2]);
	            	}
	            	else if(!line.equals("")){
		                for (int i = 0; i < keywords.length; i++) {
		                   // Pattern pattern = Pattern.compile("\\W" + keywords[i] + "\\W|^" + keywords[i] + "\\W|^" + keywords[i] + "$|\\W" + keywords[i] + "$");
		                    line = line.toLowerCase();
		                    if (line.contains(keywords[i])){
		                        keyWordsTable.put(keywords[i], keyWordsTable.get(keywords[i]).concat(", " + pageNumber));
		                    }
		                }
	            	}
	                line = br.readLine();
	            }
	        } catch (IOException e) { System.out.println("Invalid file path" + e.getMessage()); }
	 }

	 private void generatingOutputFile(String indexFilePath, String[] keywords){
		 ArrayList<String> outputArr = this.transformingOutput(keywords); 
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexFilePath))) {
	            bw.write("INDEX");
	            for (int i = 0; i < outputArr.size(); i++) {
					bw.newLine();
	            	bw.write(outputArr.get(i));	            	
				}
        } catch (IOException e) { 
            System.out.println("Some IOException has occured" + e.getMessage());
        }
	 }
	 
	 private ArrayList<String> transformingOutput(String[] keywords){
		 //String[] outputArray = new String[keyWordsTable.size()];
		 ArrayList<String> outputArray = new ArrayList<String>(keyWordsTable.size());
		 for (int i = 0; i < keywords.length; i++) {
              if (!(keywords[i] + " ").equalsIgnoreCase(keyWordsTable.get(keywords[i]))) {
                  String str = keyWordsTable.get(keywords[i]);
                  String[] tokens = str.split("[ ,]+");
                  String output = tokens[0] + ", " + tokens[1];
                  int j = 2;
                  if(tokens.length > 2){
                	  if(tokens[1].equals(tokens[2]))
                		  j=3; 
                	  else
                		  j=2;
                  }
                  
                  for (j++; j < tokens.length - 1;) {
                	  if(tokens[i].equals(tokens[i+1])) continue;
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
                  if(output != tokens[0] + ", " + tokens[1])
                	  outputArray.add(output);
              }
          }
		 	Collections.sort(outputArray);
		 	return outputArray;
	 }
}
