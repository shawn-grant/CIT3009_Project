package view.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import view.RoundedBorder;

public class DatePicker extends JPanel {
    private JComboBox<String> daySelect;
    private JComboBox<String> monthSelect;
    private JComboBox<String> yearSelect;
    private String[] days, months, years;

    public DatePicker(){
        super(new FlowLayout());
        setPreferredSize(new Dimension(250, 40));

        days = new String[32];
        months = new String[13];
        years = new String[200];
        
        days[0] = "Day";
        months[0] = "Month";
        years[0] = "Year";

        for(int i = 1; i <= 31; i++){
            days[i] = "" + i;
        }
        for(int i = 1; i <= 12; i++){
            months[i] = "" + i;
        }
        int y = 1990;
        for(int i = 1; i < 200; i++){
            years[i] = "" + y;
            y++;
        }

        Dimension boxSize = new Dimension(70, 30);

        daySelect = new JComboBox<String>(days);
        daySelect.setPreferredSize(boxSize);
        daySelect.setBorder(new RoundedBorder(7));
        
        monthSelect = new JComboBox<String>(months);
        monthSelect.setPreferredSize(boxSize);
        monthSelect.setBorder(new RoundedBorder(7));
        
        yearSelect = new JComboBox<String>(years);
        yearSelect.setPreferredSize(boxSize);
        yearSelect.setBorder(new RoundedBorder(7));

        add(daySelect);
        add(monthSelect);
        add(yearSelect);
    }

    // return the date in YY/MM/DD format
    public int[] getSelectedDate(){
        int[] date = {
            (int) yearSelect.getSelectedItem(),
            (int) monthSelect.getSelectedItem(),
            (int) daySelect.getSelectedItem(),
        };
        return date;
    }
    
}
