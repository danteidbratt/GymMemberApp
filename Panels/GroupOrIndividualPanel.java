package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class GroupOrIndividualPanel extends SuperPanel {
    
    JLabel topLabel;
    JButton groupButton;
    JLabel midLabel;
    JButton individualButton;
    JButton backButton;
    List<JButton> allButtons;
    JComboBox<String> workoutTypes;

    public GroupOrIndividualPanel() {
        this.topLabel = new JLabel();
        this.groupButton = new JButton("Group Workout");
        this.individualButton = new JButton("Individual Workout");
        this.backButton = new JButton("Back");
        this.allButtons = Arrays.asList(groupButton, individualButton, backButton);
        this.midLabel = new JLabel();
        this.workoutTypes = new JComboBox<>();
    }
    

    @Override
    public void setupPanel() {
        setLayout(new GridLayout(5, 1, 20, 10));
        setBackground(background);
        allButtons.forEach((b) -> {
            b.setFont(new Font("SansSerif", 1, 18));
        });
        
        add(topLabel);
        add(groupButton);
        add(individualButton);
        add(midLabel);
        add(backButton);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        allButtons.forEach((b) -> {
            b.addActionListener(al);
        });
    }
    
    public JButton getGroupButton() {
        return groupButton;
    }

    public JButton getIndividualButton() {
        return individualButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JComboBox<String> getWorkoutTypes() {
        return workoutTypes;
    }
}