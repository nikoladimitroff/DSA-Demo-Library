import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class BookIndexer61761 implements IBookIndexer 
{
    ArrayList<String> sorted=new ArrayList<String>();
    ArrayList<String> originalKeywords=new ArrayList<String>();
    ArrayList<String> lowercaseKeywords=new ArrayList<String>();
    HashMap<String,ArrayList<Integer>> keyword_to_pages=new HashMap<String,ArrayList<Integer>>();
    
    void make(int n,String str,String[] keywords) 
    {
        String[] words = str.split("[^a-zA-Z0-9-]+");
        for (int i=0;i<words.length;i++) 
        {
            String word = words[i];
            word = word.toLowerCase();

            if (sorted.indexOf(word)!=-1) 
            {
                ArrayList<Integer> pages = keyword_to_pages.get(word);

                if (pages==null) pages = new ArrayList<Integer>();
                else if (pages.indexOf(n)==-1) pages.add(n);
                keyword_to_pages.put(word,pages);
            }
        }
    }

    String format(ArrayList<Integer> list,String keyword) 
    {
        StringBuilder sb = new StringBuilder();

        if (list != null) 
        {
            sb.append(keyword);

            int n = list.size();
            for (int i=0;i<n; i++) 
            {
                int i1=i;
                int i2=i;
                if (i<n-1) 
                {
                    while (list.get(i2+1)-list.get(i2)==1) 
                    {
                        if (++i2>=n-1) break;
                    }
                }

                if(i1!=i2) 
                {
                    sb.append(","+list.get(i1)+"-"+list.get(i2));
                    i=i2;
                }
                else sb.append(","+list.get(i));
            }
            
            return sb.toString();
        }
        else return null;
    }
    
    @Override
    public void buildIndex(String bookFilePath,String[] keywords,String indexFilePath) 
    {
        for (int i=0;i<keywords.length;i++) 
        {
            String keyword = keywords[i];            
            originalKeywords.add(keyword);
            lowercaseKeywords.add(keyword.toLowerCase());
            sorted.add(keyword.toLowerCase());
        }

        Collections.sort(sorted);

        try 
        {            
            String myLine;
            int pageNumber = -1;
            BufferedReader myBufferedReader = new BufferedReader(new FileReader(bookFilePath));

            while ((myLine = myBufferedReader.readLine()) != null) 
            {
                if (myLine.startsWith("=== Page ") && myLine.endsWith(" ===")) 
                {
                    String numString = myLine.substring(9,myLine.length()-4);

                    try 
                    {
                        int pageNum = Integer.parseInt(numString);
                        if (pageNum != -1) {
                            pageNumber = pageNum;
                        }
                    } 
                    catch (NumberFormatException e) 
                    {
                        make(pageNumber,myLine,keywords);
                    }
                    
                } 
                else if (myLine.length() > 0) 
                {
                    make(pageNumber,myLine,keywords);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        

        try 
        {
            fos = new FileOutputStream(indexFilePath);
            osw = new OutputStreamWriter(fos);

            StringBuilder strungBuilder = new StringBuilder("INDEX");
            for (int i=0;i<sorted.size();i++) 
            {
                String keyword = sorted.get(i);
                int ind = lowercaseKeywords.indexOf(keyword);
                String original = originalKeywords.get(ind);
                String formatted = format(keyword_to_pages.get(keyword),original);
                if (formatted != null) strungBuilder.append("\r\n"+formatted);
            }

            osw.write(strungBuilder.toString());

        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                osw.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    
}
