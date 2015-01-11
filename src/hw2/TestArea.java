package hw2;

/**
 * TestArea
 * 
 * @author  Martin Grozdanov <megrozdanov@gmail.com>
 */
public class TestArea
{
    /**
     * Main function
     * 
     * @param args
     * @return void
     */
    public static void main(String[] args)
    {
        BookIndexer61616 indexer = new BookIndexer61616();

        String[] keywords = new String[] { "lorem", "quisque", "aenean", "some-dashed-word", "inexistent_word"};
        indexer.buildIndex("hw2/bookToReindex.txt", keywords, "hw2/bookIndex.txt");
    }
}