import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import royalprogrammer.IFrontBookkeeper;
import royalprogrammer.FrontBookkeeperAuthor;

/**
 * 
 * @author georgi.gaydarov
 * 
 */
public class Grader {
    private FrontBookkeeperAuthor authorEncoder;

    private static List<Class<? extends IFrontBookkeeper>> classesToBeGraded;

    private List<IFrontBookkeeper> instancesToBeGraded;

    private static void recurseWorks(File folder, List<Class<? extends IFrontBookkeeper>> result) throws ClassNotFoundException {
        File[] listOfFiles = folder.listFiles();
        Pattern homeworkPattern = Pattern.compile("FrontBookkeeper(\\d){5}.java");

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                Matcher matcher = homeworkPattern.matcher(fileName);
                if (matcher.find()) {
                    String className = fileName.replaceFirst(".java", "");
                    System.out.println("Adding class " + className + " for grading.");
                    result.add((Class<? extends IFrontBookkeeper>) Class.forName(className));
                }
            }
            else {
                recurseWorks(file, result);
            }
        }
    }
    private static List<Class<? extends IFrontBookkeeper>> getWorks() throws ClassNotFoundException {
        List<Class<? extends IFrontBookkeeper>> result = new LinkedList<Class<? extends IFrontBookkeeper>>();

        File folder = new File("./src/royalprogrammer/");
        recurseWorks(folder, result);
        return result;
    }

    private static List<IFrontBookkeeper> getEncoderInstances(List<Class<? extends IFrontBookkeeper>> classes)
        throws InstantiationException,
            IllegalAccessException {
        List<IFrontBookkeeper> result = new LinkedList<>();
        for (Class<? extends IFrontBookkeeper> clazz : classes) {
            result.add(clazz.newInstance());
        }
        return result;
    }

    public Grader() throws Exception {
        classesToBeGraded = getWorks();
        instancesToBeGraded = getEncoderInstances(classesToBeGraded);
        authorEncoder = new FrontBookkeeperAuthor();
    }

    public List<Double> gradeAllAgainst(String newsFile)
        throws Exception {

    	long authorTime=0;
        String news = new String(Files.readAllBytes(Paths.get(newsFile)),
                StandardCharsets.UTF_8);
        String[] lines = news.split(System.lineSeparator());
        String authorResult = null;
    	try {
            long authorStart = System.nanoTime();
            authorResult = authorEncoder.updateFront(lines);
            authorTime = System.nanoTime() - authorStart;
    	} catch (Throwable t) {}
        System.out.println("Author result: " + Math.round(authorTime / 1000000.0) + "ms");

        
        List<Double> results = new LinkedList<Double>();
        for (IFrontBookkeeper work : instancesToBeGraded) {            
            long workTime = 0;
            String result = null;
            try {
                long workStart = System.nanoTime();
            	result = work.updateFront(lines);
                workTime = System.nanoTime() - workStart;
            } catch (Throwable t) {
                workTime = -1;
            }
            System.out.print(work.getClass().getName() + " result: ");
            if (workTime > 0) {
                boolean correct = authorResult.replaceAll("\\s+","").equals(result != null ? result.replaceAll("\\s+","") : null);
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
        String[] tests = new String[] {
            "deaths.txt", 
            "interlist_removal.txt",
            "juggling.txt",
            "positional_attachment.txt",
            "show_soldiers.txt"
        };

        for (int i = 0; i < tests.length; i++) {
            
            System.out.println(String.format("\n\n=== Testing %s ===", tests[i]));
            List<Double> results = grader.gradeAllAgainst(tests[i]);
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
}
