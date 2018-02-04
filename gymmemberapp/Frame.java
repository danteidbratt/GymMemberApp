package gymmemberapp;

import Panels.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class Frame extends JFrame {

    JPanel topSpace;
    JPanel botSpace;
    JPanel leftSpace;
    JPanel rightSpace;
    JLabel logo;
    List<JPanel> spaces;
    Color background;
    SuperPanel currentPanel;
    LoginPanel loginPanel;
    BookOrUnbookPanel bookOrUnbookPanel;
    GroupOrIndividualPanel groupOrIndividualPanel;
    GroupPanel groupPanel;
    IndividualPanel individualPanel;
    ViewReservationsPanel viewReservationsPanel;
    List<SuperPanel> allPanels;

    public Frame() throws HeadlessException {
        this.topSpace = new JPanel();
        this.botSpace = new JPanel();
        this.leftSpace = new JPanel();
        this.rightSpace = new JPanel();
        this.spaces = Arrays.asList(topSpace, botSpace, leftSpace, rightSpace);
        this.logo = new JLabel();
        this.background = Color.WHITE;
        this.loginPanel = new LoginPanel();
        this.bookOrUnbookPanel = new BookOrUnbookPanel();
        this.groupOrIndividualPanel = new GroupOrIndividualPanel();
        this.groupPanel = new GroupPanel();
        this.individualPanel = new IndividualPanel();
        this.viewReservationsPanel = new ViewReservationsPanel();
        this.allPanels = Arrays.asList(loginPanel, bookOrUnbookPanel, groupOrIndividualPanel, groupPanel, individualPanel, viewReservationsPanel);
    }

    public void setupFrame() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        setBackground(background);
        logo.setText("- Best Gym Ever -");
        logo.setBackground(background);
        logo.setFont(new Font("SansSarif", 1, 30));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.BOTTOM);
        spaces.forEach((s) -> {
            s.setBackground(background);
            s.setOpaque(true);
            s.setPreferredSize(new Dimension(50, 60));
        });
        topSpace.setLayout(new GridLayout(1, 1));
        topSpace.add(logo);
        add(topSpace, BorderLayout.NORTH);
        add(botSpace, BorderLayout.SOUTH);
        add(leftSpace, BorderLayout.WEST);
        add(rightSpace, BorderLayout.EAST);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void updateFrame(Capsule capsule) {
        if (currentPanel != null)
            remove(currentPanel);
        switch (capsule.getState()) {
            case LOGIN:
                currentPanel = loginPanel;
                break;
            case BOOK_OR_UNBOOK:
                currentPanel = bookOrUnbookPanel;
                break;
            case GROUP_OR_INDIVIDUAL:
                currentPanel = groupOrIndividualPanel;
                break;
            case GROUP:
                currentPanel = groupPanel;
                break;
            case INDIVIDUAL:
                currentPanel = individualPanel;
                break;
            case VIEW_RESERVATIONS:
                currentPanel = viewReservationsPanel;
            default:
                break;
        }
        add(currentPanel);
        repaint();
        revalidate();
    }
    
}