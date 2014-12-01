package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree("Nikola", 1);
        bst.add("Dido", 2);
        bst.add("Aishe", 3);
        bst.add("Grigorov", 4);
        bst.add("Franklin", 4);
        bst.add("Ogi", 5);
        bst.add("Nora", 4);
        bst.add("Roro", 4);
        bst.add("Presko", 4);
        bst.add("Wii", 4);
        bst.add("Zonx", 4);
        
        System.out.println("***********");
        System.out.println(bst.left.key); // Dido
        System.out.println(bst.left.left.key); // Aishe
        System.out.println(bst.left.right.key); // Grigorov
        
        
        System.out.println("***********");
        System.out.println(bst.get("Dido")); // 2
        System.out.println(bst.get("Aishe")); // 3
        System.out.println(bst.get("Grigorov")); // 4
        System.out.println(bst.get("Nqma me")); // -1
        
        
        System.out.println("***********");
        bst.printInorder();        
        System.out.println("***********");
        //bst.remove("Zonx");
        bst.remove("Nikola");
        bst.printInorder();
        System.out.println("***********");
    }   
}
