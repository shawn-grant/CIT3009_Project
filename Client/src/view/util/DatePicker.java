/**
 * DatePicker.java
 * A standardized way to accept Date input
 *
 * @author Malik Heron & Shawn Grant
 */
package view.util;

import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class DatePicker extends JPanel {

    private final Integer[] days = new Integer[31];
    private final Integer[] years = new Integer[100];
    private final String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private JComboBox<Integer> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;

    public DatePicker() {
        super(new FlowLayout(FlowLayout.LEFT, 1, 0));
        setPreferredSize(new Dimension(270, 35));
        setIndexValues();
        initializeComponents();
        addComponentsToPanel();
    }

    public DatePicker(String[] date) {
        super(new FlowLayout(FlowLayout.LEFT, 1, 0));
        setPreferredSize(new Dimension(270, 35));
        setIndexValues();
        initializeComponents();
        getDateIndex(date);
        addComponentsToPanel();
    }

    private void initializeComponents() {
        Dimension boxSize = new Dimension(70, 35);
        Font boxFont = new Font("arial", Font.PLAIN, 14);

        dayBox = new JComboBox<>(days);
        dayBox.setFont(boxFont);
        dayBox.setPreferredSize(boxSize);
        dayBox.setBorder(new RoundedBorder(7));
        dayBox.setFocusable(false);

        monthBox = new JComboBox<>(months);
        monthBox.setFont(boxFont);
        monthBox.setPreferredSize(new Dimension(108, 35));
        monthBox.setBorder(new RoundedBorder(7));
        monthBox.setFocusable(false);

        yearBox = new JComboBox<>(years);
        yearBox.setFont(boxFont);
        yearBox.setPreferredSize(boxSize);
        yearBox.setBorder(new RoundedBorder(7));
        yearBox.setFocusable(false);
        yearBox.setSelectedIndex(40);
    }

    private void addComponentsToPanel() {
        add(dayBox);
        add(monthBox);
        add(yearBox);
    }

    // Set days and years
    private void setIndexValues() {
        for (int i = 1; i < 32; i++) {
            days[i - 1] = i;
        }
        int y = 1960;
        for (int i = 0; i < 100; i++) {
            years[i] = y;
            y++;
        }
    }

    private void getDateIndex(String[] date) {
        int index = 0;
        // Set year
        while (index < years.length) {
            if (years[index].equals(Integer.parseInt(date[0]))) {
                yearBox.setSelectedIndex(index);
            }
            index++;
        }

        // Set month
        monthBox.setSelectedIndex(Integer.parseInt(date[1]) - 1);

        index = 0;
        // Set day
        while (index < days.length) {
            if (days[index].equals(Integer.parseInt(date[2]))) {
                dayBox.setSelectedIndex(index);
            }
            index++;
        }
    }

    // return the date in YYYY-MM-DD format
    public Date getSelectedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, days[dayBox.getSelectedIndex()]);
        calendar.set(Calendar.MONTH, monthBox.getSelectedIndex());
        calendar.set(Calendar.YEAR, years[yearBox.getSelectedIndex()]);
        return calendar.getTime();
    }
}
