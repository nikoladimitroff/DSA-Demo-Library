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
    public String key;
    public int value;
    
    public Entry next;
    
    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class HashMap {
    private Entry[] table;
    private static final int INITIAL_SIZE = 2;
    private static final int GROW_MULTIPLIER = 2;
    private static final float MAX_LOAD_FACTOR = 0.75f;
    
    private int entryCount;
    
    public HashMap() {
        this.table = new Entry[INITIAL_SIZE];
        this.entryCount = 0;
    }
    
    private HashMap(int size) {
        this.table = new Entry[size];
        this.entryCount = 0;        
    }

    private int trivialHash(String content) {
        int hash = 23;
        for (int i = 0; i < content.length(); i++) {
            hash *= content.charAt(i) + 31;
        }
        return hash;
    }
    
    private int indexer(String key) {
        int hash = this.trivialHash(key);
        return Math.abs(hash % this.table.length);
    }
    
    public void add(String key, int value) {
        int index = this.indexer(key);
        Entry entry = new Entry(key, value);
        if (this.table[index] == null) {
            this.table[index] = entry;
        }
        else {
            Entry head = this.table[index];
            while (head.next != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            head.next = entry;
        }
        this.entryCount++;
        
        float loadFactor = ((float)this.entryCount) / this.table.length;
        if (loadFactor >= MAX_LOAD_FACTOR) {
            Entry[] oldTable = this.table;
            HashMap map = new HashMap(this.table.length * GROW_MULTIPLIER);
            for (int i = 0; i < oldTable.length; i++) {
                Entry head = oldTable[i];
                while (head != null) {
                    map.add(head.key, head.value);
                    head = head.next;
                }
            }
            this.table = map.table;
        }
    }
    
    public int get(String key) {
        int index = this.indexer(key);
        if (this.table[index] == null) {
            return -1;
        }
        Entry head = this.table[index];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return -1;
    }
    
    public void remove(String key) {
        int index = this.indexer(key);
        Entry head = this.table[index];
        if (head.key.equals(key)) {
            this.table[index] = null;
            this.entryCount--;
        }

        while (head.next != null) {
            if (head.next.key.equals(key)) {
                head.next = head.next.next;
                this.entryCount--;           
                return;
            }
            head = head.next;
        }
    }
    
}
