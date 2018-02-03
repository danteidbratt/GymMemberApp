package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class IndividualPanel extends SuperPanel{

    JComboBox<String> dates;
    JComboBox<String> sessions;
    JLabel midLabel1;
    JLabel midLabel2;
    JButton confirmButton;
    JButton backbButton;

    public IndividualPanel() {
        this.dates = new JComboBox<>();
        this.sessions = new JComboBox<>();
        this.midLabel1 = new JLabel();
        this.midLabel2 = new JLabel();
        this.confirmButton = new JButton("Book Workout");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(6, 1, 20, 10));
        setBackground(background);
        confirmButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(dates);
        add(sessions);
        add(midLabel1);
        add(midLabel2);
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

    public JButton getBackbButton() {
        return backbButton;
    }
    
}