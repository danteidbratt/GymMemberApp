package Panels;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.*;

public abstract class SuperPanel extends JPanel {
    
    protected final Color background;

    public SuperPanel() {
        background = Color.WHITE;
    }
    
    public void setupPanel(){}
    
    public void setActionListeners(ActionListener al){}

}