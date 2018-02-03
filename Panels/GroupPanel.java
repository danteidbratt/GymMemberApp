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
        types = new String[1];
        dates = new String [1];
        this.typeSlide = new JComboBox<>(types);
        this.dateSlide = new JComboBox<>(dates);
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
        add(typeSlide);
        add(dateSlide);
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

    public void setTypes(List<String> types) {
        this.types = (String[])types.toArray();
    }

    public void setDates(List<String> dates) {
        this.dates = (String[])dates.toArray();
    }
}