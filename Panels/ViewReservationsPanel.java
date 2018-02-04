package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ViewReservationsPanel extends SuperPanel {

    JComboBox<String> futureGroupSessionSlide;
    JComboBox<String> pastGroupSessionSlide;
    JComboBox<String> futureIndividualSessionSlide;
    JComboBox<String> pastIndividualSessionSlide;
    JLabel midLabel;
    JButton removeGroupButton;
    JButton removeIndividualButton;
    JButton backbButton;

    public ViewReservationsPanel() {
        this.futureGroupSessionSlide = new JComboBox<>(new String[1]);
        this.pastGroupSessionSlide = new JComboBox<>(new String[1]);
        this.futureIndividualSessionSlide = new JComboBox<>(new String[1]);
        this.pastIndividualSessionSlide = new JComboBox<>(new String[1]);
        this.midLabel = new JLabel();
        this.removeGroupButton = new JButton("Remove Group Reservation");
        this.removeIndividualButton = new JButton("Remove Individual Reservation");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(6, 1, 20, 10));
        setBackground(background);
        removeGroupButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(futureGroupSessionSlide);
        add(pastGroupSessionSlide);
        add(futureIndividualSessionSlide);
        add(pastIndividualSessionSlide);
        add(backbButton);
        removeGroupButton.setVisible(false);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        removeGroupButton.addActionListener(al);
        backbButton.addActionListener(al);
    }
    
    public void setFutureGroupSessionSlide(List<String> available, ActionListener al) {
        remove(futureGroupSessionSlide);
        available.add(0, "Upcoming Workouts");
        String[] sessions = new String[available.size()];
        sessions = available.toArray(sessions);
        futureGroupSessionSlide = null;
        futureGroupSessionSlide = new JComboBox<>(sessions);
        futureGroupSessionSlide.addActionListener(al);
        add(futureGroupSessionSlide, 0);
        revalidate();
    }
    
    public void setPastSessionSlide(List<String> available, ActionListener al) {
        remove(pastGroupSessionSlide);
        available.add(0, "Past Workouts");
        String[] sessions = new String[available.size()];
        sessions = available.toArray(sessions);
        pastGroupSessionSlide = null;
        pastGroupSessionSlide = new JComboBox<>(sessions);
        pastGroupSessionSlide.addActionListener(al);
        add(pastGroupSessionSlide, 1);
        revalidate();
    }

    public JButton getRemoveGroupButton() {
        return removeGroupButton;
    }

    public JButton getBackbButton() {
        return backbButton;
    }

    public JComboBox<String> getFutureGroupSessionSlide() {
        return futureGroupSessionSlide;
    }

    public JComboBox<String> getPastGroupSessionSlide() {
        return pastGroupSessionSlide;
    }
    
    public void showRemoveGroupButton(){
        
    }
    
    public void showRemoveIndividualButton(){
        
    }
    
}