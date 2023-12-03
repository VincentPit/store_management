// Central manager class for handling navigation

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
// Import other specific classes as needed


public class NavigationManager {
    private JFrame frame; 
    private JPanel cardPanel; 
    private CardLayout cardLayout; 

    public NavigationManager(JFrame frame, JPanel cardPanel, CardLayout cardLayout) {
        this.frame = frame;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
    }

    public void switchTo(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

}

