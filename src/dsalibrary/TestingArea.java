package dsalibrary;


public class TestingArea {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.enqueue(10);
        queue.enqueue(5);
       System.out.println(queue.deque());
       System.out.println(queue.deque());
    }
    
}
