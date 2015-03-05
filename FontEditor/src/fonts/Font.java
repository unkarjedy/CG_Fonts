package fonts;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by user on 28.02.2015.
 */
/* TODO: implement class */
public class Font {
    static final String DEFAULT_FONT_NAME = "Default Font Name";
    private String name = DEFAULT_FONT_NAME;
    private ArrayList<FontLetter> letters;

    Font(){
    // TODO: implement
    }

    public void addLetter(FontLetter letter){
        letters.add(letter);
    };

    public FontLetter getLetter(int i) {
        if(i < 0 || i > letters.size()-1)
            return null;

        return letters.get(i);
    }

    public int size() {return letters.size(); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
