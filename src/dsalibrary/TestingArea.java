package dsalibrary;


public class TestingArea {
    public static void main(String[] args) {
        SequentialList list = new SequentialList();
        list.insert(2, 0);
        list.insert(3, 0);
        list.insert(4, 0);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        
        System.out.println("******");
        list.insert(5, 1);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        
        
        System.out.println(list.size());
        System.out.println(list.indexOf(10));
    }
    
}
