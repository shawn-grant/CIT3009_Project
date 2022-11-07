package utils;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * For validating email address fields
 * @author Malik Heron
 */
public class EmailValidator {

    public static boolean isValid(String email, Dialog component) {
        if (checkEmail(email)) {
            return true;
        }
        JOptionPane.showMessageDialog(
                component,
                "Invalid email address",
                "Invalid Field",
                JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    private static boolean checkEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        } else {
            return pat.matcher(email).matches();
        }
    }
}