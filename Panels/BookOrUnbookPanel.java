package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class BookOrUnbookPanel extends SuperPanel{

    JLabel topLabel;
    JButton bookButton;
    JLabel midLabel;
    JButton viewReservationsButton;
    JLabel botLabel;
    JButton signOutButton;
    List<JButton> allButtons;

    public BookOrUnbookPanel() {
        this.topLabel = new JLabel();
        this.bookButton = new JButton("Book Workout");
        this.midLabel = new JLabel();
        this.viewReservationsButton = new JButton("View Reservations");
        this.botLabel = new JLabel();
        this.signOutButton = new JButton("Sign out");
        this.allButtons = Arrays.asList(bookButton, viewReservationsButton, signOutButton);
    }
    
    
    @Override
    public void setupPanel() {
        setLayout(new GridLayout(5, 1, 20, 10));
        setBackground(background);
        allButtons.forEach((b) -> {
            b.setFont(new Font("SansSerif", 1, 18));
        });
        add(topLabel);
        add(bookButton);
        add(viewReservationsButton);
        add(midLabel);
        add(signOutButton);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        allButtons.forEach((b) -> {
            b.addActionListener(al);
        });
    }

    public JButton getBookButton() {
        return bookButton;
    }

    public JButton getSignOutButton() {
        return signOutButton;
    }

    public JButton getViewReservationsButton() {
        return viewReservationsButton;
    }
    
}