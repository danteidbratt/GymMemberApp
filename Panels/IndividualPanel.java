package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class IndividualPanel extends SuperPanel{

    JComboBox<String> dateSlide;
    JComboBox<String> sessionSlide;
    JLabel midLabel1;
    JLabel midLabel2;
    JButton confirmButton;
    JButton backbButton;

    public IndividualPanel() {
        this.dateSlide = new JComboBox<>();
        this.sessionSlide = new JComboBox<>();
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
        add(dateSlide);
        add(sessionSlide);
        add(midLabel1);
        add(midLabel2);
        add(confirmButton);
        add(backbButton);
        confirmButton.setVisible(false);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        confirmButton.addActionListener(al);
        backbButton.addActionListener(al);
    }
    
    public void setDateSlide(List<String> avaliable, ActionListener al) {
        remove(dateSlide);
        avaliable.add(0, "Select Date");
        String[] dates = new String[avaliable.size()];
        dates = avaliable.toArray(dates);
        dateSlide = null;
        dateSlide = new JComboBox<>(dates);
        dateSlide.addActionListener(al);
        add(dateSlide, 0);
        revalidate();
    }
    
    public void setSessionSlide(List<String> avaliable, ActionListener al) {
        remove(sessionSlide);
        avaliable.add(0, "Select Session");
        String[] sessions = new String[avaliable.size()];
        sessions = avaliable.toArray(sessions);
        sessionSlide = null;
        sessionSlide = new JComboBox<>(sessions);
        sessionSlide.addActionListener(al);
        add(sessionSlide, 1);
        revalidate();
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getBackbButton() {
        return backbButton;
    }
    
}