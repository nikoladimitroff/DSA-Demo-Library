public interface IBookIndexer61616 {
    /**
      * Builds the index of the specified book, containing the given keywords
      * and writes it to the given output file
      * @param bookFilePath - path to book file
      * @param keywords - an array of keywords
      * @param indexFilePath - path to the output index file
      * @author Nikola Dimitroff
      */
    public void buildIndex(String bookFilePath, String[] keywords, String indexFilePath);
}