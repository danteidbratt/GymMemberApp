package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ViewReservationsPanel extends SuperPanel {

    JComboBox<String> futureSessionSlide;
    JComboBox<String> pastSessionSlide;
    JLabel midLabel;
    JButton removeButton;
    JButton backbButton;

    public ViewReservationsPanel() {
        this.futureSessionSlide = new JComboBox<>(new String[1]);
        this.pastSessionSlide = new JComboBox<>(new String[1]);
        this.midLabel = new JLabel();
        this.removeButton = new JButton("Remove Reservation");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(5, 1, 20, 10));
        setBackground(background);
        removeButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(futureSessionSlide);
        add(pastSessionSlide);
        add(midLabel);
        add(removeButton);
        add(backbButton);
        removeButton.setVisible(false);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        removeButton.addActionListener(al);
        backbButton.addActionListener(al);
    }
    
    public void setFutureGroupSessionSlide(List<String> available, ActionListener al) {
        remove(futureSessionSlide);
        available.add(0, "Upcoming Workouts");
        String[] sessions = new String[available.size()];
        sessions = available.toArray(sessions);
        futureSessionSlide = null;
        futureSessionSlide = new JComboBox<>(sessions);
        futureSessionSlide.addActionListener(al);
        add(futureSessionSlide, 0);
        revalidate();
    }
    
    public void setPastSessionSlide(List<String> available, ActionListener al) {
        remove(pastSessionSlide);
        available.add(0, "Past Workouts");
        String[] sessions = new String[available.size()];
        sessions = available.toArray(sessions);
        pastSessionSlide = null;
        pastSessionSlide = new JComboBox<>(sessions);
        pastSessionSlide.addActionListener(al);
        add(pastSessionSlide, 1);
        revalidate();
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getBackbButton() {
        return backbButton;
    }

    public JComboBox<String> getFutureSessionSlide() {
        return futureSessionSlide;
    }

    public JComboBox<String> getPastSessionSlide() {
        return pastSessionSlide;
    }
    
}