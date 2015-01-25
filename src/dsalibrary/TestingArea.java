package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        /*
        String infile = "F:\\Developer\\dsa-demo-library\\input.bin";
        String encodedFile = "F:\\Developer\\dsa-demo-library\\encoded.bin";
        String decodedFile = "F:\\Developer\\dsa-demo-library\\decoded.bin";
        
        OTPEncrypter encrypter = new OTPEncrypter();
        char[] key = new char[]{123, 245, 254, 99};
        encrypter.encrypt(infile, encodedFile, key);
        encrypter.decrypt(encodedFile, decodedFile, key);
        */
        
        AuthorBookIndexer indexer = new AuthorBookIndexer();
        String book = "F:\\\\Developer\\\\dsa-demo-library\\\\book.txt";
        //String[] keywords = new String[] { "chapter", "sea", "Ishmael", "whale", "see", "1839", "God"};
        //String[] keywords = new String[] { "2003", "Justin", "Spears", "cry", "award", "21", "FeBrUaRy"};
        //String[] keywords = new String[] { "IS", "THIS", "NOTHING", "BOY", "GALILEO", "OOH", "THE", "JUST", "BISMILLAH"};
        //String[] keywords = new String[] { "American", "beard", "vegan", "iphone", "YOLO", "kickstarter", "90", "hashtag", "bag", "8-bit", "food", "street", "mixtape", "helvetica", "photo", "wolf", "next", "cold-pressed", "post-ironic", "coffee", "mumblecore", "quinoa", "salvia", "pop-up", "try-hard", "moon", "polaroid", "craft", "tofu", "messenger", "bird", "selfies", "gluten-free", "ugh", "street", "pinterest", "leggings", "cleanse", "actually", "art", "blog", "readymade", "health", "wayfarers", "jean", "shorts", "lumbersexual", "tumblr", "retro", "single-origin", "artisan", "hoodie", "ethical", "freegan", "biodiesel", "letterpress", "mustache", "fashion", "banksy", "3"};
        String[] keywords = new String[] { "1948", "turing", "alan", "june", "test", "january", "1952", "professor", "prosecuted", "20", "19-year-old", "computer", "code", "cipher", "second", "world", "war", "mathematics", "science", "german", "39", "defence", "accused", "espionage", "death", "september", "breaking", "Church", "cambridge", "Entscheidungsproblem", "Christopher", "Morcom"};
        indexer.buildIndex(book, keywords, "INDEX.txt");
    }   
}
