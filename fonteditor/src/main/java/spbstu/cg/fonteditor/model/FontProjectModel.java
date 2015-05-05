package spbstu.cg.fonteditor.model;

import spbstu.cg.fontcommons.font.Font;
import spbstu.cg.fontcommons.font.Letter;
import spbstu.cg.fontcommons.utils.Logger;

import java.util.ArrayList;

/**
 * Created by Egor Gorbunov on 28.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FontProjectModel {
    private final Logger logger;
    private Font font;
    private ArrayList<LetterEditorModel> editedLetters;

    public FontProjectModel(String fontName, Logger logger) {
        this.logger = logger;
        font = new Font(fontName);
        editedLetters = new ArrayList<>(20);
    }

    public FontProjectModel(Font font, Logger logger) {
        this.font = font;
        this.logger = logger;
        editedLetters = new ArrayList<>(font.getLetters().size());

        for (Letter l : font.getLetters().values()) {
            editedLetters.add(new LetterEditorModel(l, logger));
        }
    }

    public LetterEditorModel addNewLetter(Character character) {
        if (font.getLetter(character) != null) {
            return null;
        }

        Letter letter = new Letter(character);

        font.addLetter(character, letter);

        LetterEditorModel newLetterEditorModel = new LetterEditorModel(letter, logger);
        editedLetters.add(newLetterEditorModel);

        return newLetterEditorModel;
    }

    public LetterEditorModel getLetterEditorModel(int index) {
        if (index >= 0 && index < editedLetters.size())
            return editedLetters.get(index);
        return null;
    }

    public Font getFont() {
        for (LetterEditorModel model : editedLetters) {
            model.updateLetterBoundingBox();
        }
        return font;
    }

    public String getFontName() {
        if (font != null)
            return font.getName();
        else {
            return "";
        }
    }

    public int getLetterNumber() {
        return editedLetters.size();
    }


    public boolean contains(char c) {
        return font.getLetter(c) != null;
    }

}
