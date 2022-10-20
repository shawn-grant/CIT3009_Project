package view.components;

import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class DatePicker extends JPanel {

    private final JComboBox<Integer> dayBox;
    private final JComboBox<String> monthBox;
    private final JComboBox<Integer> yearBox;
    private final Integer[] days = new Integer[31];
    private final String[] months = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    private final Integer[] years = new Integer[100];

    public DatePicker() {
        super(new FlowLayout());
        setPreferredSize(new Dimension(250, 40));

        for (int i = 1; i < 32; i++) {
            days[i - 1] = i;
        }
        int y = 1960;
        for (int i = 0; i < 100; i++) {
            years[i] = y;
            y++;
        }

        Dimension boxSize = new Dimension(70, 30);

        dayBox = new JComboBox<>(days);
        dayBox.setPreferredSize(boxSize);
        dayBox.setBorder(new RoundedBorder(7));
        dayBox.setFocusable(false);

        monthBox = new JComboBox<>(months);
        monthBox.setPreferredSize(boxSize);
        monthBox.setBorder(new RoundedBorder(7));
        monthBox.setFocusable(false);

        yearBox = new JComboBox<>(years);
        yearBox.setPreferredSize(boxSize);
        yearBox.setBorder(new RoundedBorder(7));
        yearBox.setFocusable(false);
        yearBox.setSelectedIndex(40);

        add(dayBox);
        add(monthBox);
        add(yearBox);
    }

    // return the date in MM DD, YYYY format
    public String getSelectedDate() {
        return months[monthBox.getSelectedIndex()] + " " +
                days[dayBox.getSelectedIndex()] + ", " +
                years[yearBox.getSelectedIndex()];
    }

}
