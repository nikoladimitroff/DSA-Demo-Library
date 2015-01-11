package hw2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookIndexer61704 implements IBookIndexer {

	@Override
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {

		File index = new File(indexFilePath);
		File book = new File(bookFilePath);
		
		TreeMap<String, ArrayList<Integer>> keywordsMap= new TreeMap<String, ArrayList<Integer>>();
		for (int i = 0; i < keywords.length; i++) {
			keywordsMap.put(keywords[i].toLowerCase(), new ArrayList<Integer>());
		}
	
		Pattern pat = Pattern.compile("=== page ([0-9]+) ===");
		
		try {
			if (!index.exists())
				index.createNewFile();
				
			if(book != null)
				{
					Scanner sc = new Scanner(book);
					int currentPage = 0;
					while(sc.hasNextLine())
					{
						String line = sc.nextLine().toLowerCase();
						Matcher m = pat.matcher(line);
						if(m.matches())
						{
							String[] words = line.split(" ");
							currentPage = Integer.parseInt(words[2]);
						}
						else
						{
							String[] words = line.split(" ");
							for(String s : words)
							{
								s = s.trim().replaceAll("[^a-z-]", "");
								if(!s.equals("") && keywordsMap.containsKey(s))
								{
									if(!keywordsMap.get(s).contains(currentPage))
									{
										keywordsMap.get(s).add(currentPage);
									}
								}
							}
						}
					}
					
					FileWriter fw = new FileWriter(index);
					BufferedWriter writer = new BufferedWriter(fw);
					boolean consequentPages = false;
					int begining = 0;
					
					writer.write("INDEX");
					writer.newLine();
					for(Entry<String, ArrayList<Integer>> entry : keywordsMap.entrySet())
					{
						writer.write(entry.getKey() + ", ");
						for (int i = 0; i < entry.getValue().size(); i++) {
							if(i < entry.getValue().size() - 1 && entry.getValue().get(i+1) != entry.getValue().get(i) + 1)
							{
								if(i < entry.getValue().size() - 1)
								{
									writer.write(entry.getValue().get(i) + ", ");
								}
								else
								{
									writer.write(entry.getValue().get(i));
								}
								consequentPages = false;
								begining = 0;
							}
							else if(i < entry.getValue().size() - 1 && entry.getValue().get(i+1) == entry.getValue().get(i) + 1)
							{
								consequentPages = true;
								if(begining == 0){
								begining = entry.getValue().get(i);
								writer.write(begining + "-");
								}
							}
							else{
								writer.write((entry.getValue().get(i)).toString());
								begining = 0;
							}
						}
						writer.newLine();
					}
					writer.close();
					//System.out.println(keywordsMap.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}