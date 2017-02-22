import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;


public class BookIndexer61685 implements IBookIndexer{

	@Override
	public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath) {
		HashMap<String, ArrayList<Integer>> outMap = new HashMap<String, ArrayList<Integer>>();
		String [] as = readFile(bookFilePath);
		for(int page = 1; page < as.length; page++) {
			for(String word: keywords) {
				int pos = 0;
				while ((pos = as[page].toLowerCase().indexOf(word.toLowerCase(), pos)) != -1) {
					int last = pos != 0 ? pos - 1 : pos;
					int next = (pos + word.length()) > as[page].length() ? as[page].length() : pos + word.length();
				    if(isValid(as[page].charAt(last), last == 0)  && isValid(as[page].charAt(next), next == as[page].length())) {
				    	if(outMap.containsKey(word))
				    		outMap.get(word).add(page);	
				    	else
				    		outMap.put(word, new ArrayList<Integer>(Arrays.asList(page)));
				    	break;
				    }
				    pos++;
				}
			}
		}
		formatOutput(outMap, indexFilePath);
	}
	
	private void formatOutput(HashMap<String, ArrayList<Integer>> map, String indexFilePath) {
		String output = "INDEX" + System.getProperty("line.separator");
		SortedSet<String> keys = new TreeSet<String>(map.keySet());
		for (String key : keys) { 
			output += key + ", " + formatPage(map.get(key));
		}
		try {
			Files.write(Paths.get(indexFilePath), output.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String formatPage(ArrayList<Integer> pageList) {
		String output = "";
		int end = -1;
		for(int i = 0; i < pageList.size(); i++) {
			if(i == 0)
				output += pageList.get(i);
			else {
				for(; i < pageList.size(); i++) {
					if(pageList.get(i) - 1 == pageList.get(i-1)) {
						end = pageList.get(i);
						continue;
					}
					break;
				}
				
				if(end != -1) {
					i--;
					output += "-" + end;
					end = -1;
				}
				else {
					output += ", " + pageList.get(i);
				}
				
			}
		}
		output += System.getProperty("line.separator");
		return output;
    }
		
	private boolean isValid(char symbol, boolean valid) {
		if(valid || (symbol != '-' && !isNumeric(symbol)))
			return true;
		return false;
	}

	private boolean isNumeric(char num) {
        if(num >= '0' && num <= '9')
        	return true;
        return false;
    }
	
	
	public static String[] readFile(String path)
	{
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new String(encoded, Charset.defaultCharset()).split("=== Page [0-9]{1,10} ===");
	}


}
