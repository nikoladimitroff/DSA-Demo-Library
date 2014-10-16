package dsalibrary;

class Node {
    public int element;
    
    public Node left;
    public Node right;
    
    public Node(int element) {
        this.element = element;
    }
    
    public Node(int element, Node left, Node right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
}


public class LinkedList {
    Node head;
    Node tail;
    
    public void add(int element) {
        if (this.tail == null) {
            Node node = new Node(element);
            this.head = node;
            this.tail = node;
            return;
        }
        
        Node node = new Node(element);
        node.left = this.tail;
        this.tail.right = node;
        this.tail = node;
    }
    
    public Node get(int index) {
        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.right;
        }
        return node;
    }
    
    public void insert(int element, Node leftNeighbour) {
        Node node = new Node(element);
        Node rightNeigbhour = leftNeighbour.right;
        leftNeighbour.right = node;
        rightNeigbhour.left = node;
        node.left = leftNeighbour;
        node.right = rightNeigbhour;        
    }
    
    public int size() {
        if (this.head == null)
            return 0;
        
        Node node = this.head;
        int size = 1;
        while (node.right != null) {
            node = node.right;
            size++;
        }
        return size;
    }
    
    public Node nodeOf(int element) {
        if (this.head == null) {
            return null;
        }
        
        Node node = this.head;
        while (node.right != null) {
            if (node.element == element)
                return node;
            node = node.right;
        }
        if (node.element == element) {
            return node;
        }
        return null;
    }
}
