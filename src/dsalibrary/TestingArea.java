package dsalibrary;


public class TestingArea {
    public static void main(String[] args) {
       LinkedList list = new LinkedList();
       list.add(5);
       list.add(123);
       list.add(321);
       
       list.insert(1024, list.get(1));
        System.out.println(list.get(0).element);
        System.out.println(list.get(1).element);
        System.out.println(list.get(2).element);
        System.out.println(list.get(3).element);
        System.out.println(list.size());
        System.out.println(list.nodeOf(5).element);
    }
    
}
