package utils;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * For validating currency fields
 * @author Malik Heron
 */
public class CostValidator {

    public static boolean isValid(String str, Dialog component) {
        BigDecimalValidator validator = CurrencyValidator.getInstance();
        BigDecimal amount = validator.validate(str, Locale.ENGLISH);

        if(amount != null){
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    component,
                    "Field for cost contains invalid character/s",
                    "Invalid Field", JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
    }
}