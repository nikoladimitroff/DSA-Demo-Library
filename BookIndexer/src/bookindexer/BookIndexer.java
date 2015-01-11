
package bookindexer;

/**
 *
 * @author Tonkata
 */
public class BookIndexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BookIndexer61736 book = new BookIndexer61736();
        String[] keywords = new String[3];
        keywords[0]="lorem";
        keywords[1]="quisque";
        keywords[2]="aenean";
        book.buildIndex("D:\\book.txt", keywords, "D:\\index.txt");
    }
    
}
