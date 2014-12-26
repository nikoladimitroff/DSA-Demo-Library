Homework 2
==========
## Homework information
Due date: 23:59:59, 11th of January, 2015.

You are to submit a single **.zip** archive named **groupNumber.fn.hw2.zip**
where **groupNumber** is your group and **fn** is your faculty number.
Upload the archive to moodle.

This is ok: ***group1.66666.hw1.zip***

This is not: ***domashno2.rar***

Your zip archive must contain only **.java** files - no folders, no other files.

Additionally, **you'll improve your grade** by +1 if you commit your work in github
and +2 if you send us a pull a request at 
* https://github.com/NikolaDimitroff/DSA-Demo-Library for groups 1 and 2
* https://github.com/jorenca/SDA-Tasks for groups 4 and 5

## Task - File Encryption
Build a book's index table
=======

Most book have an index of keywords found within its text. For example:

![Random book index][book-index-img]

Your task today will be to generate one.
You are to implement the following interface:

```java
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
```

We expect you to implement the interface in a class named `BookIndexerFN`
where FN is your faculty number (suprise!). Like this:
```java
public class BookIndexer66666 implements IBookIndexer {
    ...
}
```

The book will have the following format:

```
=== Page 1 ===
Text of page 1
=== Page 2 ===
Text of page 2
...
=== Page m ===
Text of page m
```

We expect your index file to look like this:
```
INDEX
keyword1, page1
keyword2, page1, page2, page3
...
keywordN, page1, page2-page3
```
Rules:
* The output file must always start with the string *"INDEX"*
* The next **n** lines contain the locations of each of the **n** keywords, **sorted alphabetically**:
  - Each line must start with the keyword itself, followed by a comma and a space
  - The indexes of the words follow. If a word is found several times, display
  them all separated by commas. If a word is found on **consequent** pages display
  the range of pages with a dash as in ***150-152*** instead of ***150, 151, 152***.
  - The indexer is case-insenstive - consider *"Hello"* and *"hello"* the same word
  - **No** trailing whitespace is allowed
  - **No** trailing line ending is allowed

Here's an example:

Contents of the book file:
```
=== Page 1 ===
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut rhoncus non odio sed ultricies. Pellentesque dui metus, cursus at neque eget, sollicitudin sagittis lorem. Nullam semper ex et rutrum tincidunt.

=== Page 2 ===
Aenean porta velit et ex fermentum, vitae finibus diam elementum. Vivamus dapibus purus dolor, quis ultricies orci ornare eu. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque sed purus rhoncus, dignissim nunc non, laoreet tortor. 

== Page 3 ===
Integer blandit sem nulla, in pretium augue finibus quis. Ut elementum augue vel dignissim accumsan. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nec blandit libero.
```
Keywords:
```
String[] keywords = new String[] { "lorem", "quisque", "aenean" };
```

Then the output index file should look like this:
```
INDEX
aenean, 2
lorem, 1, 3
quisque, 2-3
```

## Notes
* There might be missing pages (page 10 might be followed by page 100)
* Pages will be presented in order (if x > y then page x will appear after page y)
* Keywords will never contain whitespace characters.
* The book files will be <= 300KB long
* The number of keywords will be <= 1000

[book-index-img]: https://lh3.googleusercontent.com/q13SsDI-o1ezCleJPiNYE6_oNf5C3G7bsXTanu2gjQWCeK9v9NinYYlj5LckXxdECJrm9htGxzWEenqTMjnxfLWvVo9srP-UKvRZacfzCqzNEWL-F-U

## Grading scale

We'll run your solution on several tests and compare it with ours using the following
formula:

`performance = sum(authorSolutionTime[i] / yourSolutionTime[i]) / testCount`
(`i` is for every test example)

Your marks will be assigned using this sample code:

```java
int grade = 2;
if (performance > 0.7)
    grade = 6;
else if (performance > 0.5)
    grade = 5;
else if (performance > 0.3)
    grade = 4;
else if (performance > 0.1)
    grade = 3;
```

If any of your tests gives a wrong result, you'll get 0 points for it.

If you cheat by copying your solution from a classmate or the internet, you'll get 0
points for the **ALL** tests.
