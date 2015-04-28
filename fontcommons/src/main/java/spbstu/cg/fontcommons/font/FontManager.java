package spbstu.cg.fontcommons.font;

import java.awt.*;
import java.io.*;

/**
 * Created by user on 05.03.2015.
 */
public class FontManager {

    public static Font loadFontFromFile(String filename) {
        try(ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(filename))) {
            Font font = (Font) in.readObject();
            for(Letter letter : font.getLetters().values()) {
                letter.normalize();
            }
            return font;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveFontToFile(Font font, String filename) {
        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(font);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
