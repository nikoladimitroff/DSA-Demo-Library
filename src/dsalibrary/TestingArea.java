package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        SequentialList list = new SequentialList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(100);
        list.add(1000);
        list.add(1023);
        list.add(4321);
                
        RecursiveFunctions.binarySearch(list, 5);
        
        long now = System.currentTimeMillis();
        String expr = "4 + (5 * (4 + (3)))";
        System.out.println(RecursiveFunctions.braceMatching(expr));
        System.out.println(System.currentTimeMillis() - now);
    }   
}
