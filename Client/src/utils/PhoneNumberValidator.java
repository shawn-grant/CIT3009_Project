package utils;

import javax.swing.*;
import java.awt.*;

/**
 * For validating phone number fields
 * Source: "<a href="https://www.javatpoint.com/mobile-number-validation-in-java">...</a>"
 * @author Malik Heron
 */
public class PhoneNumberValidator {

    //function to check if the mobile number is valid or not
    public static boolean isValid(String number, Dialog component) {
        if (checkPhone(number)) {
            return true;
        }
        JOptionPane.showMessageDialog(
                component,
                "Invalid telephone number",
                "Invalid Field",
                JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    private static boolean checkPhone(String number) {
        //validates phone numbers having 10 digits (9998887776)
        if (number.matches("\\d{10}"))
            return true;
        //validates phone numbers having digits, -, . or spaces
        else if (number.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
        else if (number.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}"))
            return true;
        //validates phone numbers having digits and extension (length 3 to 5)
        else if (number.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
            return true;
        //validates phone numbers having digits and area code in braces
        else if (number.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
            return true;
        else //return false if any of the input matches is not found
            if (number.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}"))
            return true;
        else return number.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}");
    }
}