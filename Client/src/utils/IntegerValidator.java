package utils;

import javax.swing.*;
import java.awt.*;

public class IntegerValidator {

    public static boolean isValid(String str, Dialog component) {
        if (checkString(str)) {
            return true;
        }
        JOptionPane.showMessageDialog(
                component,
                "One or more fields contains invalid characters",
                "Warning", JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    private static boolean checkString(String str) {
        //Check if string is only Letters
        for (int i = 0; i < str.length(); i++) {
            boolean flag = Character.isDigit(str.charAt(i));

            if (!flag) {
                return false;
            }
        }
        return true;
    }
}
