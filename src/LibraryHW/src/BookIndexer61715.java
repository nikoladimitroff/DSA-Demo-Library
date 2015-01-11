import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class BookIndexer61715 implements IBookIndexer {
	private static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	private static List<Keyword> formatKeyWords(String[] keywords){
		List<Keyword> kwords = new ArrayList<Keyword>();
		TreeSet<String> treeSet = new TreeSet<String>();		
		for(String word: keywords){
			treeSet.add(word.toLowerCase());
		}
		
		for(String word: treeSet){
			kwords.add(new Keyword(word));
		}
		
		return kwords;
	}
	
	@Override
	public void buildIndex(String bookFilePath, String[] keywords,
			String indexFilePath) {		
		List<Keyword> kwords = formatKeyWords(keywords);		
		String content = null;
		try {
			content = readFile(bookFilePath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] con = content.split("\r\n");
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for(int i = 0; i < con.length; i++){
			
			if(con[i].trim().matches("=== Page \\d+ ===")){
				int number = Integer.parseInt(con[i].split(" ")[2]);
				pageNumbers.add(number);
			}
		}
		String[] pages = content.split("=== Page \\d+ ===");
		
		for(int i = 0; i < pageNumbers.size(); i++){
			String pageText = pages[i + 1].toString().toLowerCase();
			int pageNumber = pageNumbers.get(i);			
			String[] strings = pageText.split("[^A-Za-z0-9-]");				
			for(Keyword word: kwords){
				for(String string: strings){
					if(word.content.equals(string)){
						word.pages.add(pageNumber);
					}
				}
				
				//if(pageText.matches(".*?\\b" + word.content + "\\b.*?")){
//					if(pageText.matches(".*?[^A-Za-z0-9-]" + word.content + "[^A-Za-z0-9-].*?")){
//					word.pages.add(pageNumber);
//				}
			}
		}
		
		StringBuilder result = new StringBuilder();
		result.append("INDEX");
		for(Keyword word: kwords){
			if(!word.pages.isEmpty()){
			result.append("\r\n").append(word.Output());
			}
		}

		PrintWriter out = null;
		try {
			out = new PrintWriter(indexFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String resultToString = result.toString().trim();
		
		out.print(resultToString);
		out.close();		
	}
	
	public static class Keyword{
		public List<Integer> pages;
		public String content;
		public Keyword(String word){
			this.content = word;
			this.pages = new ArrayList<Integer>();
		}

		public String Output(){
			if(this.pages.size() == 0){
				return null;
			}
			
			StringBuilder result = new StringBuilder();
			result.append(this.content);
			
			for(int i = 0; i < this.pages.size(); i++){
				result.append(", " + this.pages.get(i));
				while(i < this.pages.size() - 1 && this.pages.get(i) == this.pages.get(i + 1) - 1){
					i++;
					if((i == this.pages.size() - 1)||(i < this.pages.size() - 1 && this.pages.get(i) != this.pages.get(i + 1) - 1)){
						result.append("-" + this.pages.get(i));
					}					
				}
			}
			
			return result.toString();
		}
	}
}
