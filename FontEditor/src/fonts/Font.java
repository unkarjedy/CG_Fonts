package fonts;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by user on 28.02.2015.
 */
/* TODO: implement class */
public class Font {
    private String name = "Default Font Name";
    private ArrayList<FontLetter> letters;

    Font(){}

    public void saveFontToFile(String fileName) {}

    public void addLetter(FontLetter letter){
        letters.add(letter);
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
