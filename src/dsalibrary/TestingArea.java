package dsalibrary;


public class TestingArea {
    
    public static void main(String[] args) {
        
        String infile = "F:\\Developer\\dsa-demo-library\\input.bin";
        String encodedFile = "F:\\Developer\\dsa-demo-library\\encoded.bin";
        String decodedFile = "F:\\Developer\\dsa-demo-library\\decoded.bin";
        
        OTPEncrypter encrypter = new OTPEncrypter();
        char[] key = new char[]{123, 245, 254, 99};
        encrypter.encrypt(infile, encodedFile, key);
        encrypter.decrypt(encodedFile, decodedFile, key);
    }   
}
