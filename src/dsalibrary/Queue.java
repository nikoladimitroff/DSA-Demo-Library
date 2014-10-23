/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsalibrary;

public class Queue {
    private SequentialList container;
    
    public Queue() {
        this.container = new SequentialList();
    }
    
    public int head() {
        return this.container.get(0);
    }
    
    public void enqueue(int element) {
        this.container.add(element);
    }
    
    public int deque() {
        int element = this.container.get(0);
        this.container.removeAt(0);
        return element;
    }
    
    public int size() {
        return this.container.size();
    }
}
