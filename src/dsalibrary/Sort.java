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
        int min = array[start];
        int minIndex = start;
        for (int i = start + 1; i < end; i++) {
            if (array[i] < min) {
                min = array[i];  
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
        for (int i = start; i < end - 1; i++) {
            int j = i + 1;
            while (j > start && array[j] < array[j - 1]) {
                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
                j--;                
            }
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
        array[storage++] = temp;

        return pivot;
    }
    
    public static void quicksort(int[] array, int start, int end) {
        if (end - start <= 1)
            return;

        int pivot = partition(array, start, end);
        quicksort(array, start, pivot);
        quicksort(array, pivot, end);
    }
    
    /*
    
    
        System.out.print("PARTITION: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print(' ');
        }
        System.out.println();
    
    
    
    */
    
    
    public static int[] mergesort(int[] array) {
        if (array.length <= 1)
            return array;
        
        int mid = array.length / 2;
        int[] leftHalf = mergesort(copy(array, 0, mid));
        int[] rightHalf = mergesort(copy(array, mid, array.length));
        int[] result = new int[array.length];
        int leftIndex = 0,
            rightIndex = 0;
        int storeIndex = 0;
        while (leftIndex < leftHalf.length || rightIndex < rightHalf.length) {
            if (leftIndex < leftHalf.length && 
                rightIndex >= rightHalf.length) {
                result[storeIndex++] = leftHalf[leftIndex++];
            }
            else if (rightIndex < rightHalf.length && 
                leftIndex >= leftHalf.length) {
                result[storeIndex++] = rightHalf[rightIndex++];
            }
            else if (leftHalf[leftIndex] < rightHalf[rightIndex]) {
                result[storeIndex++] = leftHalf[leftIndex++];
            }
            else {
                result[storeIndex++] = rightHalf[rightIndex++];
            }
        }
        return result;
    }
    
    public static int[] copy(int[] array, int start, int end) {
        int[] copy = new int[end - start];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = array[i + start];
        }
        return copy;
    }
    
}       
