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
public class RunLengthEncoder {

    public void encode(String inFile, String outFile) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(inFile));
            out = new BufferedOutputStream(new FileOutputStream(outFile));

            int inBuff = in.read();
            int currentRepeatingSequenceLength;
            
            while (inBuff >= 0) {
                char input = (char) inBuff;
                currentRepeatingSequenceLength = 0;
                while (inBuff >= 0 && (char)inBuff == input) {
                    currentRepeatingSequenceLength++;
                    inBuff = in.read(); 
                }
                if (currentRepeatingSequenceLength > 3) {
                    out.write(255);
                    out.write(input);
                    out.write(currentRepeatingSequenceLength);
                }
                else {
                    for (int i = 0; i < currentRepeatingSequenceLength; i++) {
                        out.write(input);
                    }
                }
            }

            in.close();
            out.flush();
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void decode(String encodedFile, String outFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(encodedFile));
            out = new BufferedOutputStream(new FileOutputStream(outFile));

            int inBuff;
            
            while ((inBuff = in.read()) >= 0) {
                char input = (char) inBuff;
                if (input != 255) {
                    out.write(input);
                }
                else {
                    char symbol = (char) in.read();
                    char repetitionLength = (char) in.read();
                    for (int i = 0; i < repetitionLength; i++) {
                        out.write(symbol);
                    }
                }
            }

            in.close();
            out.flush();
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
