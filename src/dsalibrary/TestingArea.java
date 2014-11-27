package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree("Nikola", 1);
        bst.add("Oto", 2);
        bst.add("Pesho", 3);
        bst.add("Mechka", 4);
        
        bst.printPreorder(0);
        
        System.out.println("***********");
        System.out.println(bst.get("Mechka"));
    }   
}
