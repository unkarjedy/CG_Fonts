package spbstu.cg.texteditor.model;

import spbstu.cg.font.Letter;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by JDima on 26/04/15.
 */
public class TextEditorModel {
    private float size;
    private String font;
    private HashMap<String, HashMap<Character, Letter> > fonts;
    private HashMap<Character, Letter> alphabet;
    private LinkedList<Character> text;

    public TextEditorModel() {
        text = new LinkedList<>();
    }

    public LinkedList<Character> getText() {
        return text;
    }


    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public HashMap<Character, Letter> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(HashMap<Character, Letter> alphabet) {
        this.alphabet = alphabet;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void deleteLastLetter() {
        if (!text.isEmpty()) {
            text.pop();
        }
    }

    public void addLetter(String actionCommand) {
        text.add(actionCommand.charAt(0));
    }

    public void clearLetters() {
        if (!text.isEmpty()) {
            text.clear();
        }
    }

    public void saveText() {
        //TODO SAVE TEXT
    }

    public void addNewFont() {
        //TODO NEW FONT
    }

    public void loadText() {
        //TODO LOAD TEXT
    }
}
