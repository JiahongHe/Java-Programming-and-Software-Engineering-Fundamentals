
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class WordPlay {
    public boolean isVowel (char ch) {
        String vocab = "aeiou";
        if (vocab.indexOf(Character.toLowerCase(ch)) != 0) {
            return true;
        }
        return false;
    }
    
    public String replaceVowels(String phrase, char ch) {
        int N = phrase.length();
        StringBuilder original = new StringBuilder(phrase);
        //StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            char tmp = original.charAt(i);
            if (isVowel(tmp)) {
                original.setCharAt(i, '*');
            }
        }
        return original.toString();
    }
    
    public String emphasize(String phrase, char ch) {
        int N = phrase.length();
        StringBuilder original = new StringBuilder(phrase);
        //StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            char tmp = original.charAt(i);
            if (isVowel(tmp)) {
                if (i % 2 == 0) {
                    original.setCharAt(i, '*');
                }
                if (i % 2 == 1) {
                    original.setCharAt(i, '+');
                }
            }
        }
        return original.toString();
    }
}
