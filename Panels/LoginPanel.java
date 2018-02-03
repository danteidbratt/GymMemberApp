package Panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginPanel extends SuperPanel {

    private final JPanel botSpace;
    private final JLabel nameText;
    private final JLabel infoText;
    private final JTextField textField;
    private final JButton signInButton;
    private final JButton exitButton;
    

    public LoginPanel() {
        this.botSpace = new JPanel();
        this.nameText = new JLabel("Name:");
        this.infoText = new JLabel();
        this.textField = new JTextField();
        this.signInButton = new JButton("Sign in");
        this.exitButton = new JButton("Exit");
    }

    @Override
    public void setupPanel() {
        setLayout(new GridLayout(5, 1, 0, 8));
        setBackground(background);
        
        nameText.setFont(new Font("SansSarif", 0, 16));
        nameText.setHorizontalAlignment(SwingConstants.CENTER);
        nameText.setVerticalAlignment(SwingConstants.BOTTOM);
        
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("SansSarif", 2, 18));
        
        infoText.setFont(new Font("SansSarif", 0, 12));
        infoText.setHorizontalAlignment(SwingConstants.CENTER);
        
        signInButton.setFont(new Font("SansSarif", 1, 16));
        exitButton.setFont(new Font("SansSarif", 1, 16));
        
        botSpace.setBackground(background);
        botSpace.setOpaque(true);
        
        add(nameText);
        add(textField);
        add(signInButton);
        add(infoText);
        add(exitButton);
    }

    @Override
    public void setActionListeners(ActionListener al) {
        textField.addActionListener(al);
        signInButton.addActionListener(al);
        exitButton.addActionListener(al);
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JLabel getInfoText() {
        return infoText;
    }

    public JButton getExitButton() {
        return exitButton;
    }

}