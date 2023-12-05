package models;// Central manager class for handling navigation

import javax.swing.*;
import java.awt.*;
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

