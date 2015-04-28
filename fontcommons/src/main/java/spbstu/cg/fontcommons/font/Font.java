package spbstu.cg.fontcommons.font;

import java.io.Serializable;
import java.util.*;

/**
 * Created by user on 28.02.2015.
 */
/* TODO: implement class */
public class Font implements Serializable{
    private String name;

    private Map<Character, Letter> letters;

    public Font(String fontName) {
        name = fontName;
        letters = new TreeMap<>();
    }

    public void addLetter(Character character, Letter letter){
        if (letter == null || character == null)
            throw new NullPointerException();

        letters.put(character, letter);
    }

    public Letter getLetter(Character character) {
        return letters.get(character);
    }

    public Map<Character, Letter> getLetters() {return letters;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void normalize() {
        for (Letter l : letters.values()) {
            l.normalize();
        }
    }
}
