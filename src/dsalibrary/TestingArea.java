package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        HashMap hashmap = new HashMap();
        hashmap.add("Nikola", 359898);
        hashmap.remove("Nikola");
        System.out.println(hashmap.get("Nikola"));
    }   
}
