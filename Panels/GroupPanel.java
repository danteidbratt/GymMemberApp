package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class GroupPanel extends SuperPanel{

    String[] types;
    String[] dates;
    String[] sessions;
    JComboBox<String> typeSlide;
    JComboBox<String> dateSlide;
    JComboBox<String> sessionSlide;
    JLabel midLabel;
    JButton confirmButton;
    JButton backbButton;

    public GroupPanel() {
        this.types = new String[0];
        this.dates = new String[0];
        this.sessions = new String[0];
        this.typeSlide = new JComboBox<>(types);
        this.dateSlide = new JComboBox<>(dates);
        this.sessionSlide = new JComboBox<>(sessions);
        this.midLabel = new JLabel();
        this.confirmButton = new JButton("Book Workout");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(6, 1, 20, 10));
        setBackground(background);
        midLabel.setFont(new Font("SansSerif", 2, 15));
        midLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(typeSlide);
        add(dateSlide);
        add(sessionSlide);
        add(midLabel);
        add(confirmButton);
        add(backbButton);
        confirmButton.setVisible(false);
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

    public void setTypeSlide(List<String> available, ActionListener al) {
        remove(typeSlide);
        available.add(0, "Select Workout");
        typeSlide = null;
        types = new String[available.size()];
        types = available.toArray(types);
        typeSlide = new JComboBox<>(types);
        typeSlide.addActionListener(al);
        add(typeSlide, 0);
        revalidate();
    }

    public void setDateSlide(List<String> avaliable, ActionListener al) {
        remove(dateSlide);
        avaliable.add(0, "Select Date");
        dates = new String[avaliable.size()];
        dates = avaliable.toArray(dates);
        dateSlide = null;
        dateSlide = new JComboBox<>(dates);
        dateSlide.addActionListener(al);
        add(dateSlide, 1);
        revalidate();
    }

    public void setSessionSlide(List<String> available, ActionListener al) {
        remove(sessionSlide);
        available.add(0, "Select Session");
        sessions = new String[available.size()];
        sessions = available.toArray(sessions);
        sessionSlide = null;
        sessionSlide = new JComboBox<>(sessions);
        sessionSlide.addActionListener(al);
        add(sessionSlide, 2);
        revalidate();
    }
    
    public void addComboBoxes(){
    }

    public JComboBox<String> getTypeSlide() {
        return typeSlide;
    }

    public JComboBox<String> getDateSlide() {
        return dateSlide;
    }

    public JComboBox<String> getSessionSlide() {
        return sessionSlide;
    }
    
    public void displayChoice(){
        midLabel.setText(typeSlide.getSelectedItem().toString() + " / " +
                         dateSlide.getSelectedItem().toString() + " / " +
                         sessionSlide.getSelectedItem().toString());
    }
    
    public void hideChoice(){
        midLabel.setText("");
    }
    
    public void resetDateSlide(){
        remove(dateSlide);
        dateSlide = new JComboBox<>(new String[1]);
        add(dateSlide, 1);
    }
    
    public void resetSessionSlide(){
        remove(sessionSlide);
        sessionSlide = new JComboBox<>(new String[1]);
        add(sessionSlide, 2);
    }
    
    public void showConfirmButton(){
        confirmButton.setVisible(true);
    }
    
    public void hideConfirmButton(){
        confirmButton.setVisible(false);
    }
}