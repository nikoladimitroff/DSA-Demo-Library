/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsalibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Nikola
 */
public class OTPEncrypter {
    
    public void encrypt(String inFile, String outFile, char[] key) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(inFile));
            out = new BufferedOutputStream(new FileOutputStream(outFile));

            int inBuff;
            int position = 0;
            
            while ((inBuff = in.read()) >= 0) {
                char input = (char) inBuff;
                char result = (char)(input ^ key[position]);
                out.write(result);
                
                position++;
            }

            in.close();
            out.flush();
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void decrypt(String encodedFile, String outFile, char[] key) {
        this.encrypt(encodedFile, outFile, key);
    }
}
