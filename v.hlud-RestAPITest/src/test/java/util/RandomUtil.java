package util;

import nl.flotsam.xeger.Xeger;

public class RandomUtil {
    public static String generateRandomString() {
        Xeger xeger = new Xeger("([:word:]){5}");
        String strGen = xeger.generate();
        return strGen;
    }
}

