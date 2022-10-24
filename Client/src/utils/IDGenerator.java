package utils;

/**
 * @author Shawn Grant
 */
public class IDGenerator {

    public String getID(String letter) {
        int num = (int) ((Math.random() * (2000 - 100)) + 100);
        return letter + num;
    }
}
