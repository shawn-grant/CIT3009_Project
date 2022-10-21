package view.util;

import java.util.regex.Pattern;

/**
 * @author Malik Heron
 */
public class EmailVerifier {

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        } else {
            return pat.matcher(email).matches();
        }
    }
}