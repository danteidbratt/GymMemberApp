package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ViewReservationsPanel extends SuperPanel {

    JComboBox<String> reservedGroupSessionSlide;
    JComboBox<String> reservedIndividualSessionSlide;
    JLabel midLabel;
    JButton removeGroupButton;
    JButton removeIndividualButton;
    JButton backbButton;

    public ViewReservationsPanel() {
        this.reservedGroupSessionSlide = new JComboBox<>(new String[1]);
        this.reservedIndividualSessionSlide = new JComboBox<>(new String[1]);
        this.midLabel = new JLabel();
        this.removeGroupButton = new JButton("Remove Reservation");
        this.removeIndividualButton = new JButton("Remove Reservation");
        this.backbButton = new JButton("Back");
    }
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(4, 1, 20, 10));
        setBackground(background);
        removeGroupButton.setFont(new Font("SansSerif", 1, 18));
        backbButton.setFont(new Font("SansSerif", 1, 18));
        add(reservedGroupSessionSlide);
        add(reservedIndividualSessionSlide);
        add(midLabel);
        add(backbButton);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        removeGroupButton.addActionListener(al);
        removeIndividualButton.addActionListener(al);
        backbButton.addActionListener(al);
    }
    
    public void setReservedGroupSessionSlide(List<String> options, ActionListener al) {
        remove(0);
        options.add(0, "Reserved Group-Workouts");
        String[] sessions = new String[options.size()];
        sessions = options.toArray(sessions);
        reservedGroupSessionSlide = null;
        reservedGroupSessionSlide = new JComboBox<>(sessions);
        reservedGroupSessionSlide.addActionListener(al);
        add(reservedGroupSessionSlide, 0);
        revalidate();
    }
    
    public void resetReservedGroupSessionSlide(){
        reservedGroupSessionSlide.setSelectedIndex(0);
    }
    
    public void setReservedIndividualSessionSlide(List<String> options, ActionListener al) {
        remove(1);
        options.add(0, "Reserved Individual Workouts");
        String[] sessions = new String[options.size()];
        sessions = options.toArray(sessions);
        reservedIndividualSessionSlide = null;
        reservedIndividualSessionSlide = new JComboBox<>(sessions);
        reservedIndividualSessionSlide.addActionListener(al);
        add(reservedIndividualSessionSlide, 0);
        revalidate();
    }
    
    public void resetReservedIndividualSessionSlide(){
        reservedIndividualSessionSlide.setSelectedIndex(0);
    }
    
    public JButton getRemoveGroupButton() {
        return removeGroupButton;
    }

    public JButton getBackbButton() {
        return backbButton;
    }

    public JComboBox<String> getReservedGroupSessionSlide() {
        return reservedGroupSessionSlide;
    }

    public JComboBox<String> getReservedIndividualSessionSlide() {
        return reservedIndividualSessionSlide;
    }

    public void showRemoveGroupButton(){
        remove(2);
        add(removeGroupButton,2);
        
    }
    
    public void showRemoveIndividualButton(){
        remove(2);
        add(removeIndividualButton,2);
    }
    
    public void hideRemoveButtons(){
        remove(2);
        add(midLabel,2);
    }
    
}