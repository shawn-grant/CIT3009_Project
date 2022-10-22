package utils;

import javax.swing.*;
import java.awt.*;

public class StringValidator {

    public static boolean isValid(String str, Dialog component) {
        if (checkString(str)) {
            return true;
        }
        JOptionPane.showMessageDialog(
                component,
                "One or more fields contains invalid characters",
                "Invalid Field", JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    private static boolean checkString(String str) {
        //Check if string is only Letters
        for (int i = 0; i < str.length(); i++) {
            boolean flag = Character.isLetter(str.charAt(i));

            if (!flag) {
                return false;
            }
        }
        return true;
    }
}
