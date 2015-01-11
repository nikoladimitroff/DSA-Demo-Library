/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sda_hw2_official;

/**
 *
 * @author icho9_000
 */

public interface IBookIndexer {
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
