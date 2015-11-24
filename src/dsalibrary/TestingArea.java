package dsalibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class StringComparator implements IComparator<String> {
    @Override
    public boolean isLessThan(String x, String y) {
        if (x.length() != y.length()) {
            return x.length() < y.length();
        }
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) != y.charAt(i)) {
                return x.charAt(i) < y.charAt(i);
            }
        }
        return false;
    }
}


public class TestingArea {
    private static final boolean ENABLE_DEBUG_PRINTING = true;

    private static void printArrayInline(int[] array, int start, int end) {
        if (!ENABLE_DEBUG_PRINTING) {
            return;
        }
        for (int i = start; i < end; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
    private static int[] getInverselySortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    
    private static double measureWorstCase(int size) {
        int[] data = getInverselySortedArray(size);
        printArrayInline(data, 0, size);

        double now = System.currentTimeMillis();
        Sort.bubbleSort(data, 0, size);
        double duration = System.currentTimeMillis() - now;

        printArrayInline(data, 0, size);
        return duration;
    }
    
    private static int[] loadData(String path, int expectedSize) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        int[] array = new int[expectedSize];

        for (int i = 0; i < expectedSize; i++) {
            array[i] = s.nextInt();
        }
        s.close();
        return array;
    }
    
    private static double measureAverageCase(int[] array, int size) {
        int[] dataCopy = array.clone();
        printArrayInline(dataCopy, 0, size);

        double now = System.currentTimeMillis();
        Sort.bubbleSort(dataCopy, 0, size);
        double duration = System.currentTimeMillis() - now;

        printArrayInline(dataCopy, 0, size);
        return duration;
    }
    
    private static int binarySearch(int array[], int element, int start, int end) {
        if (end - start == 0) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (array[mid] < element) {
            return binarySearch(array, element, mid + 1, end);
        }
        else if (array[mid] > element) {
            return binarySearch(array, element, start, mid);
        }
        else {
            return mid;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        //String[] stringArray = new String[] { "XYA", "ASD", "YXZ", "DSA", "XYZ" };
        //Sort.quickSort(stringArray, 0, stringArray.length, new StringComparator());
        //for (String s : stringArray) {
        //     System.out.println(s);
        //}

        int[] array = new int[] { 4, 2, 5, 3, 10, -5, 7, -10, 0, 9 };
        Sort.selectionSort(array, 0, array.length);
        printArrayInline(array, 0, array.length);
        System.out.println(binarySearch(array, 10, 0, array.length));

        int maxSize = 100000;
        int minSize = 10000;
        int step = 5000;
        
        int[] averageCaseData = loadData("random_numbers.txt", maxSize);
        for (int size = minSize; size <= maxSize; size += step) {
        //    System.out.println(measureAverageCase(averageCaseData, size));
        }
    }   
}
