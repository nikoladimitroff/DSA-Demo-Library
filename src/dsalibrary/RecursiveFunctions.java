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
public class RecursiveFunctions {  
    static int[] memory = new int[1000];
    
    public static int factoriel(int n) {
        if (n == 0 || n == 1)
            return 1;
        return n * factoriel(n - 1);        
    }
    
    public static int pow(int x, int y) {
        if (y == 0)
            return 1;
        return x * pow(x, y - 1);        
    }
    
    public static int karatsubaPow(int x, int y) {
        if (y == 0)
            return 1;
        
        if (y % 2 == 0) {
            int xToZ = karatsubaPow(x, y / 2);
            return xToZ * xToZ;
        }
        return karatsubaPow(x, y - 1) * x;
    }
    
    public static int fibonacci(int n) {
        if (n == 0 || n == 1)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);    
    }
    
    public static int fibonacciMemoized(int n) {
        if (memory[n] == 0) {       
            if (n == 0 || n == 1)
                memory[n] = 1;
            else
                memory[n] = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
        }
        return memory[n];
    }
    public static int fibonacciDP(int n) {
        for (int i = 0; i <= n; i++) {
            if (i == 0 || i == 1)
                memory[i] = 1;
            else
                memory[i] = memory[i - 1] + memory[i - 2];
        }
        return memory[n];
    }
    
    static int binarySearchImplementation(SequentialList list,
                                            int element,
                                            int left,
                                            int right) {
       // *********
        if (left == right) {
            if (list.get(left) == element) {
                return left;
            }
            return -1;
        }
        
        int mid = (left + right) / 2;
        if (list.get(mid) == element)
            return mid;
        // ************
        else if (list.get(mid) < element) {
            return binarySearchImplementation(list, element, mid + 1, right);
        }
        else {
            return binarySearchImplementation(list, element, left, mid - 1);
        }
    }
    
    public static int binarySearch(SequentialList list, int element) {
        return binarySearchImplementation(list, element, 0, list.size());
    }
    
    static int braceMatchingImplementation(String expression, int start) {
        for (int i = start; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (symbol == '(') {
                int poppedIndex = braceMatchingImplementation(expression, i + 1);
                if (poppedIndex == expression.length())
                    return -1;
                if (poppedIndex == -1)
                    return -1;
                if (expression.charAt(poppedIndex) != ')')
                    return -1;
                i = poppedIndex;
            }
            if (symbol == ')')
                return i;
        }
        return expression.length();
    }
    
    public static boolean braceMatching(String expression) {
        return braceMatchingImplementation(expression, 0) == expression.length();
    }
}
