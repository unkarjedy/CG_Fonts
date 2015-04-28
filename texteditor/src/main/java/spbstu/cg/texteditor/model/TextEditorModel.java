package spbstu.cg.texteditor.model;

import spbstu.cg.fontcommons.font.Font;
import spbstu.cg.fontcommons.font.Letter;
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
    private Font font;
    private HashMap<String, Font> fonts;
    private LinkedList<Character>   text;

    public TextEditorModel() {
        fonts = new HashMap<>();
        text = new LinkedList<>();
    }

    public LinkedList<Character> getText() {
        return text;
    }


    public float getSize() {
        return size;
    }

    public Letter getLetter(Character c) {
        if (font != null){
            return font.getLetter(c);
        }
        return null;
    }

    public void setFont(String font) {
        this.font = fonts.get(font);
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getAlphabet() {
        StringBuilder sb = new StringBuilder();
        for (Character c : font.getLetters().keySet()) {
            sb.append(c + " ");
        }
        return sb.toString();
    }

    public void deleteLastLetter() {
        if (!text.isEmpty()) {
            text.removeLast();
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

            bufferedWriter.write(font.getName());
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

    public void addNewFont(Font font) {
        this.font = font;
        fonts.put(font.getName(), font);
    }

    public void loadText(List<String> newText) {
        if (newText.size() < 2) {
            JOptionPane.showMessageDialog(new JFrame(), Consts.INCORRECT_FORMAT, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String fontName = newText.get(0);
        if (fonts.keySet().contains(fontName)) {
            JOptionPane.showMessageDialog(new JFrame(), Consts.INCORRECT_FONT, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            return;

        } else {
            text.clear();
            font = fonts.get(fontName);
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
