package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class GroupPanel extends SuperPanel{

    String[] types;
    String[] dates;
    JComboBox<String> typeSlide;
    JComboBox<String> dateSlide;
    JComboBox<String> sessions;
    JLabel midLabel;
    JButton confirmButton;
    JButton backbButton;

    public GroupPanel() {
        types = new String[0];
        dates = new String[0];
        this.sessions = new JComboBox<>();
        this.midLabel = new JLabel();
        this.confirmButton = new JButton("Book Workout");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(6, 1, 20, 10));
        setBackground(background);
        confirmButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(sessions);
        add(midLabel);
        add(confirmButton);
        add(backbButton);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        confirmButton.addActionListener(al);
        backbButton.addActionListener(al);
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(JButton confirmButton) {
        this.confirmButton = confirmButton;
    }

    public JButton getBackbButton() {
        return backbButton;
    }

    public void setBackbButton(JButton backbButton) {
        this.backbButton = backbButton;
    }

    public void setTypeSlide(List<String> types) {
        types.add(0, "Any Workout");
        this.types = types.toArray(this.types);
        this.typeSlide = new JComboBox<>(this.types);
    }

    public void setDateSlide(List<String> dates) {
        dates.add(0, "Any Date");
        this.dates = dates.toArray(this.dates);
        this.dateSlide = new JComboBox<>(this.dates);
    }
    
    public void addComboBoxes(){
        add(typeSlide, 0);
        add(dateSlide, 1);
    }
}