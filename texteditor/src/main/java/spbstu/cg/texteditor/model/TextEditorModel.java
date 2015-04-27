package spbstu.cg.texteditor.model;

import spbstu.cg.font.Letter;
import spbstu.cg.texteditor.Consts;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        fonts = new HashMap<>();
        alphabet = new HashMap<>();
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

    //TODO add ALPHABET
    public String getAlphabet() {
        /*StringBuilder sb = new StringBuilder();
        for (Character c : alphabet.keySet()) {
            sb.append(c + " ");
        }
        return sb;*/
        return "A B C D ...";
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

    public void saveText(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //TODO font
            bufferedWriter.write("own");
            bufferedWriter.write("\n");

            for (int i = 0; i < text.size(); i++) {
                bufferedWriter.write(text.get(i));
            }

            bufferedWriter.close();
        }
        catch (Exception e)
        {
            System.out.println(filename);
            System.out.println("IOException : " + e);
        }
    }

    public String addNewFont() {
        return null;
        //TODO NEW FONT
    }

    public void loadText(List<String> newText) {
        if (newText.size() < 2) {
            JOptionPane.showMessageDialog(new JFrame(), Consts.INCORRECT_FORMAT, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String fontName = newText.get(0);
        //TODO conditon
        //if (fonts.keySet().contains(fontName))
        if (false) {
            JOptionPane.showMessageDialog(new JFrame(), Consts.INCORRECT_FONT, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            return;

        } else {
            text.clear();
            alphabet = fonts.get(fontName);
            for (int i = 1; i < newText.size(); i++) {
                System.out.print(newText.get(i) + "\n");
                char[] line = newText.get(i).toCharArray();
                for (int j = 0; j < line.length; j++) {
                    text.add(line[j]);
                }
            }
        }
    }
}
