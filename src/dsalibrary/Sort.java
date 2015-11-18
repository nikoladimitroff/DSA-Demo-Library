/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsalibrary;

/**
 *
 * @author Nikola
 */

public class Sort {
    
    public static int min(int array[], int start, int end) {
        int minIndex = start;
        for (int i = start; i < end; i++) {
            if (array[i] < array[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void selectionSort(int[] array, int start, int end) {
        for (int i = start; i < end; i++) {
            int minIndex = min(array, i, end);
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
    
    public static void bubbleSort(int[] array, int start, int end) {
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = start; i < end - 1; i++) {
                if (array[i + 1] < array[i]){
                    int temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    done = false;
                }
            }
        }
    }
    
    public static void insertionSort(int[] array, int start, int end) {
        for (int i = start + 1; i < end; i++) {
            int elementToSwap = array[i];
            int j = i;
            // Go from i to start to save some cache lines
            while (j > start && array[j - 1] > elementToSwap) {
                array[j] = array[j - 1];
                j--;                
            }
            
            array[j] = elementToSwap;
        }
    }
    
    private static int partition(int[] array, int start, int end) {
        int pivot = end - 1;
        int storage = start;
        for (int i = start; i < end; i++) {
            if (array[i] < array[pivot]) {
                int temp = array[i];
                array[i] = array[storage];
                array[storage++] = temp;
            }
        }
        int temp = array[pivot];
        array[pivot] = array[storage];
        array[storage] = temp;

        return storage;
    }
    
    public static void quickSort(int[] array, int start, int end) {
        if (end - start <= 1)
            return;

        int pivot = partition(array, start, end);
        quickSort(array, start, pivot);
        quickSort(array, pivot + 1, end);
    }
    
    // Quick sort will fallback to insertion
    public static void quickSortWithInsertion(int[] array, int start, int end) {
        if (end - start <= 20) { // This constant is somewhat arbitrary
            insertionSort(array, start, end);
            return;
        }

        int pivot = partition(array, start, end);
        quickSort(array, start, pivot);
        quickSort(array, pivot + 1, end);
    }
    
    // Quick sort with custom comparator
    private static <T> int partition(T[] array, int start, int end, IComparator<T> comparator) {
        int pivot = end - 1;
        int storage = start;
        for (int i = start; i < end; i++) {
            if (comparator.isLessThan(array[i], array[pivot])) {
                T temp = array[i];
                array[i] = array[storage];
                array[storage++] = temp;
            }
        }
        T temp = array[pivot];
        array[pivot] = array[storage];
        array[storage++] = temp;

        return pivot;
    }
    
    public static <T> void quickSort(T[] array, int start, int end, IComparator<T> comparator) {
        if (end - start <= 1)
            return;

        int pivot = partition(array, start, end, comparator);
        quickSort(array, start, pivot, comparator);
        quickSort(array, pivot, end, comparator);
    }
}       
