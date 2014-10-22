package dsalibrary;


public class TestingArea {
    public static void main(String[] args) {
       LinkedList list = new LinkedList();
       list.add(123);
       list.add(456);
       list.add(789);
       
       list.insert(101112, list.get(0));
       list.insert(321, null);
       System.out.println(list.size());
    }
    
}
