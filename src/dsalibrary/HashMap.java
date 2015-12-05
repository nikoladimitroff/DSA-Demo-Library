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

class Entry {
    public Entry next;
    public String key;
    public int value;
    
    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

public class HashMap {
    private Entry[] table;
    private int elementCount;
    
    private static final int INITIAL_SIZE = 16;
    private static final double SIZE_MULTIPLIER = 2;
    private static final double MAX_WORKLOAD = 0.75;
    
    HashMap() {
        this.elementCount = 0;
        this.table = new Entry[INITIAL_SIZE];
    }
    
    private int hash(String key) {
        int p1 = 23;
        int p2 = 31;
        int result = p1;
        for (int i = 0; i < key.length(); i++) {
            result += p1 * key.charAt(i) + p2;
        }
        result += p1 * key.length() + p2;
        return result;
    }
    
    private int index(String key, int size) {
        return Math.abs(this.hash(key)) % size;
    }
    
    private void tryGrow() {
        double workload = this.elementCount / this.table.length;
        
        if (workload >= MAX_WORKLOAD) {
            int newSize = (int)(this.table.length * SIZE_MULTIPLIER);
            Entry[] newTable = new Entry[newSize];
            for (int i = 0; i < this.table.length; i++) {
                Entry current = this.table[i];
                while (current != null) {
                    int position = this.index(current.key, newSize);
                    Entry nextElement = current.next;
                    if (newTable[position] == null) {
                        newTable[position] = current;
                    }
                    else {
                        Entry head = newTable[position];
                        while (head.next != null) {
                            head = head.next;
                        }
                        head.next = current;
                    }
                    current.next = null;
                    current = nextElement;
                }
            }
            this.table = newTable;
        }
    }
    
    public void add(String key, int value) {
        tryGrow();
        int position = this.index(key, this.table.length);
        Entry head = this.table[position];
        if (head == null) {
            this.table[position] = new Entry(key, value);
            this.elementCount++;
            return;
        }
        while (head.next != null && !head.key.equals(key)) {
            head = head.next;
        }
        if (head.key.equals(key)) {
            head.value = value;
            return;
        }
        head.next = new Entry(key, value);
        this.elementCount++;
    }
    
    public int get(String key) {
        int position = this.index(key, this.table.length);
        Entry current = this.table[position];
        while (current != null && !current.key.equals(key)) {
            current = current.next;
        }
        if (current == null) {
            throw new RuntimeException("No element with this key");
        }
        else {
            return current.value;
        }
    }
    
    public int size() {
        return this.elementCount;
    }
    
}
