package view.components;

import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class DatePicker extends JPanel {
    private final JComboBox<Integer> daySelect;
    private final JComboBox<String> monthSelect;
    private final JComboBox<Integer> yearSelect;
    private final Integer[] days = new Integer[31];
    private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
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

        daySelect = new JComboBox<>(days);
        daySelect.setPreferredSize(boxSize);
        daySelect.setBorder(new RoundedBorder(7));
        daySelect.setFocusable(false);

        monthSelect = new JComboBox<>(months);
        monthSelect.setPreferredSize(boxSize);
        monthSelect.setBorder(new RoundedBorder(7));
        monthSelect.setFocusable(false);

        yearSelect = new JComboBox<>(years);
        yearSelect.setPreferredSize(boxSize);
        yearSelect.setBorder(new RoundedBorder(7));
        yearSelect.setFocusable(false);
        yearSelect.setSelectedIndex(40);

        add(daySelect);
        add(monthSelect);
        add(yearSelect);
    }

    // return the date in YY/MM/DD format
    public String getSelectedDate() {
        return months[monthSelect.getSelectedIndex()] + " " +
                days[daySelect.getSelectedIndex()] + ", " +
                years[yearSelect.getSelectedIndex()];
    }

}
