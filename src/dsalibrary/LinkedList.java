package dsalibrary;

class Node {
    public int element;
    public Node right;
    public Node left;
    
    public Node(int element) {
        this.element = element;
    }
}

class LinkedList {
    private Node head;
    private Node tail;
    
    public void add(int element) {
        if (this.head == null) {
            this.head = new Node(element);
            this.tail = this.head;
        }
        else {
            Node node = new Node(element);
            this.tail.right = node;
            node.left = this.tail;
            this.tail = node;
        }
    }
    
    public Node get(int index) {
        if (this.head == null)
            return null;
        
        Node current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.right;
        }
        return current;
    }
    
    public Node nodeOf(int element) {
        Node current = this.head;
        while (current != null) {
            if (current.element == element) 
                return current;
            current = current.right;
        }
        return null;
    }
    
    public void insert(int element, Node left) {
        Node node = new Node(element);
        if (left == null) {
            this.head.left = node;
            node.right = this.head;
            this.head = node;
        }
        else {
            node.left = left;
            node.right = left.right;
            left.right = node;
            node.right.left = node;
        }
    }
    
    public int size() {
        int size = 0;
        Node current = this.head;
        while (current != null) {
            size++;
            current = current.right;
        }
        return size;
    }
    
    
}