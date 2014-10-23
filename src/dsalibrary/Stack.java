/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsalibrary;

/*
  Last in, first  out  - Stack = LIFO
  First in, first out - queue = FIFO
  First in, last out
  Last in, last out

*/

public class Stack {
    private SequentialList container;
    
    public Stack() {
        this.container = new SequentialList();
    }
    
    // Returns the top of the stack
    int peek() {
        return container.get(container.size() - 1);
    }
    
    public int pop() {
        int element = this.peek();
        container.removeAt(container.size() - 1);
        return element;
    }
    
    public void push(int element) {
        this.container.add(element);
    }
    
    public int size() {
        return this.container.size();
    }
}
