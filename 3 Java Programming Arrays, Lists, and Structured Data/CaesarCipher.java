
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import edu.duke.*;

public class CaesarCipher {
    public String encrypt(String input, int key) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shifted = alphabet.substring(key) + alphabet.substring(0, key);
        alphabet += alphabet.toLowerCase();
        shifted += shifted.toLowerCase();
        int N = input.length();
        StringBuilder res = new StringBuilder(input);
        for (int i = 0; i < N; i++) {
            if(alphabet.indexOf(input.substring(i, i+1)) != -1) {
                res.setCharAt(i, shifted.charAt(alphabet.indexOf(input.substring(i, i+1))));
            }
        }
        return res.toString();
    }
    
    public String encryptTwoKeys(String input, int key1, int key2) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shifted1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        String shifted2 = alphabet.substring(key2) + alphabet.substring(0, key2);
        alphabet += alphabet.toLowerCase();
        shifted1 += shifted1.toLowerCase();
        shifted2 += shifted2.toLowerCase();
        int N = input.length();
        StringBuilder res = new StringBuilder(input);
        for (int i = 0; i < N; i++) {
            if(alphabet.indexOf(input.substring(i, i+1)) != -1) {
                if (i % 2 == 0) {
                    res.setCharAt(i, shifted1.charAt(alphabet.indexOf(input.substring(i, i+1))));
                }
                if (i % 2 == 1) {
                    res.setCharAt(i, shifted2.charAt(alphabet.indexOf(input.substring(i, i+1))));
                }
            }
        }
        return res.toString();
    }
    
    public void quickTest() {
        String msg1 = "FIRST LEGION ATTACK EAST FLANK!";
        String msg2 = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        String msg3 = "First Legion";
        System.out.println(encrypt(msg2, 15));
        System.out.println(encryptTwoKeys(msg2, 8, 21));
    }
    
    public void testCaesar() {
        FileResource fr = new FileResource();
        int key = 23;
        String message = fr.asString();
        String encrypted = encrypt(message, key);
        System.out.println("key is " + key + "\n" + encrypted);
        //String input = "FIRST LEGION ATTACK EAST FLANK!";
        //System.out.println(encrypt(input, 23));
    }
}
