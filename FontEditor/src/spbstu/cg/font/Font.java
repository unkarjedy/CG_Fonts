package spbstu.cg.font;

import java.util.ArrayList;

/**
 * Created by user on 28.02.2015.
 */
/* TODO: implement class */
public class Font {
    static final String DEFAULT_FONT_NAME = "Default Font Name";
    private String name = DEFAULT_FONT_NAME;
    private ArrayList<Letter> letters;

    Font(){
    // TODO: implement
    }

    public void addLetter(Letter letter){
        letters.add(letter);
    };

    public Letter getLetter(int i) {
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
