package spbstu.cg.fontcommons.font;

import java.util.*;

/**
 * Created by user on 28.02.2015.
 */
/* TODO: implement class */
public class Font {
    private String name;

    private Map<Character, Letter> letters;

    public Font(String fontName) {
        name = fontName;
        letters = new TreeMap<>();
        // TODO: implement
    }

    public void addLetter(Character character, Letter letter){
        if (letter == null || character == null)
            throw new NullPointerException();

        letters.put(character, letter);
    }

    public Letter getLetter(Character character) {
        return letters.get(character);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
