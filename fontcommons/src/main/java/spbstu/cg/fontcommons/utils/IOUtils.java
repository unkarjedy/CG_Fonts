package spbstu.cg.fontcommons.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Egor Gorbunov on 19.05.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class IOUtils {
    public static byte[] readResource(String filename, int maxSize, Class resourceClass) {
        byte[] buffer = new byte[maxSize];
        InputStream imageStream = resourceClass.getResourceAsStream(filename);
        try {
            imageStream.read(buffer, 0, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
