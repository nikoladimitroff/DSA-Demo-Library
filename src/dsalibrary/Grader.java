package dsalibrary;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author georgi.gaydarov
 * 
 */
public class Grader {
    private IBookIndexer authorEncoder;

    private static List<Class<? extends IBookIndexer>> classesToBeGraded;

    private List<IBookIndexer> instancesToBeGraded;

    private static List<Class<? extends IBookIndexer>> getWorks() throws ClassNotFoundException {
        List<Class<? extends IBookIndexer>> result = new LinkedList<Class<? extends IBookIndexer>>();

        File folder = new File("./src/");
        File[] listOfFiles = folder.listFiles();
        Pattern homeworkPattern = Pattern.compile("BookIndexer(\\d){5}.java");

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                Matcher matcher = homeworkPattern.matcher(fileName);
                if (matcher.find()) {
                    String className = fileName.replaceFirst(".java", "");
                    System.out.println("Adding class " + className + " for grading.");
                    result.add((Class<? extends IBookIndexer>) Class.forName(className));
                }
            }
        }

        return result;
    }

    private static List<IBookIndexer> getEncoderInstances(List<Class<? extends IBookIndexer>> classes)
        throws InstantiationException,
            IllegalAccessException {
        List<IBookIndexer> result = new LinkedList<IBookIndexer>();
        for (Class<? extends IBookIndexer> clazz : classes) {
            result.add(clazz.newInstance());
        }
        return result;
    }

    public Grader() throws Exception {
        classesToBeGraded = getWorks();
        instancesToBeGraded = getEncoderInstances(classesToBeGraded);
        authorEncoder = new AuthorBookIndexer();
    }

    public List<Double> gradeAllAgainst(String bookFile, String[] keywords)
        throws Exception {
        String authorOutFile = "author.out";
        String gradedOutFile = "graded.out";

        File aoutf = new File(gradedOutFile);
    	aoutf.delete();
    	long authorTime=0;
    	try {
            // copy the keywords as some of you like to fiddle with them
            String[] keywordsCopy = Arrays.copyOf(keywords, keywords.length);
            long authorStart = System.nanoTime();
            authorEncoder.buildIndex(bookFile, keywordsCopy, authorOutFile);
            authorTime = System.nanoTime() - authorStart;
    	} catch (Throwable t) {}
        System.out.println("Author result: " + Math.round(authorTime / 1000000.0) + "ms");

        
        List<Double> results = new LinkedList<Double>();
        for (IBookIndexer work : instancesToBeGraded) {
        	File outf = new File(gradedOutFile);
        	outf.delete();
            
        	long workTime = 0;
            try {
                long workStart = System.nanoTime();
            	work.buildIndex(bookFile, keywords, gradedOutFile);
                workTime = System.nanoTime() - workStart;
            } catch (Throwable t) {
                workTime = -1;
            }

            
            System.out.print(work.getClass().getName() + " result: ");
            if (workTime > 0) {
                boolean correct = readAndCompareFiles(gradedOutFile, authorOutFile);
                if (correct) {
                    DecimalFormat df = new DecimalFormat("#.#####");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    double workTimeResultFraction = ((double) authorTime) / workTime;
                    results.add(workTimeResultFraction);
                    System.out.println(df.format(workTimeResultFraction) + " (time " + Math.round(workTime / 1000000.0)
                            + "ms)");
                } else {
                    results.add(0.0);
                    System.out.println("Incorrect. (time " + Math.round(workTime / 1000000.0)
                            + "ms)");
                }
            } else {
                results.add(0.0);
                System.out.println("Threw an exception.");
            }

        }

        return results;
    }

    public static void main(String[] args) throws Exception {
        Grader grader = new Grader();

        List<List<Double>> allResults = new ArrayList<List<Double>>(10);
        String[] books = new String[] {
            "moby-dick.txt", 
            "hipster-lorem-ipsum.txt",
            "turing.txt",
            "cry-me-a-river.txt",
            "bohemian-rhapsody.txt"
        };

        String[][] keywords = new String[][] {
            new String[] { "chapter", "sea", "Ishmael", "whale", "see", "1839", "God"},
            new String[] { "American", "beard", "vegan", "iphone", "YOLO", "kickstarter", "90", "hashtag", "bag", "8-bit", "food", "street", "mixtape", "helvetica", "photo", "wolf", "next", "cold-pressed", "post-ironic", "coffee", "mumblecore", "quinoa", "salvia", "pop-up", "try-hard", "moon", "polaroid", "craft", "tofu", "messenger", "bird", "selfies", "gluten-free", "ugh", "street", "pinterest", "leggings", "cleanse", "actually", "art", "blog", "readymade", "health", "wayfarers", "jean", "shorts", "lumbersexual", "tumblr", "retro", "single-origin", "artisan", "hoodie", "ethical", "freegan", "biodiesel", "letterpress", "mustache", "fashion", "banksy", "3"},
            new String[] { "1948", "turing", "alan", "june", "test", "january", "1952", "professor", "prosecuted", "20", "19-year-old", "computer", "code", "cipher", "second", "world", "war", "mathematics", "science", "german", "39", "defence", "accused", "espionage", "death", "september", "breaking", "Church", "cambridge", "Entscheidungsproblem", "Christopher", "Morcom"},
            new String[] { "2003", "Justin", "Spears", "cry", "award", "21", "FeBrUaRy"},
            new String[] { "IS", "THIS", "NOTHING", "BOY", "GALILEO", "OOH", "THE", "JUST", "BISMILLAH"}
        };

        for (int i = 0; i < books.length; i++) {
            
            System.out.println(String.format("\n\n=== Testing %s ===", books[i]));
            List<Double> results = grader.gradeAllAgainst(books[i], keywords[i]);
            allResults.add(results);
        }

        double finalResults[] = new double[classesToBeGraded.size()];

        for (List<Double> allResultsFromTest : allResults) {
            for (int i = 0; i < allResultsFromTest.size(); i++) {
                finalResults[i] += allResultsFromTest.get(i);
            }
        }

        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("\n\n\n=== FINAL RESULTS ===");
        for (int i = 0; i < finalResults.length; i++) {
            finalResults[i] /= allResults.size();

            System.out.println(classesToBeGraded.get(i).getName() + ": " + df.format(finalResults[i]));
        }

    }


    /**
     * Fetched from PatchedTestGen.java (some changes applied).
     * 
     * @param pathToCurrentFile
     * @param pathToExpectedFile
     * @return
     */
    private static boolean readAndCompareFiles(String pathToCurrentFile, String pathToExpectedFile) throws Exception {
        InputStream current = null;
        InputStream expected = null;
        try {
            current = new BufferedInputStream(new FileInputStream(pathToCurrentFile));
            expected = new BufferedInputStream(new FileInputStream(pathToExpectedFile));
            int currentChar;
            int expectedChar;
            while ((currentChar = current.read()) != -1 && (expectedChar = expected.read()) != -1) {
                // ignore \r as this causes difference in the output
                if (currentChar == '\r')
                    currentChar = current.read();
                if (expectedChar == '\r')
                    expectedChar = expected.read();

                if (currentChar != expectedChar) {
                    return false;
                }
            }
            if(current.read() != expected.read()) return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (current != null) {
                current.close();
            }
            if (expected != null) {
                expected.close();
            }
        }
        return true;
    }
}
