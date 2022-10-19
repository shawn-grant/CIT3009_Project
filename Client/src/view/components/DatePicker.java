package view.components;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class DatePicker extends JPanel {
    private JComboBox<Integer> daySelect;
    private JComboBox<Integer> monthSelect;
    private JComboBox<Integer> yearSelect;
    private Integer[] days, months, years;

    public DatePicker(){
        setLayout(new FlowLayout());
        setPreferredSize(getPreferredSize());

        for(int i = 1; i <= 30; i++){
            days[i-1] = i;
        }
        for(int i = 1; i <= 12; i++){
            months[i-1] = i;
        }
        for(int i = 1990; i <= 2050; i++){
            years[i-1] = i;
        }

        daySelect = new JComboBox<Integer>(days);
        monthSelect = new JComboBox<Integer>(months);
        yearSelect = new JComboBox<Integer>(years);
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
