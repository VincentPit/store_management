
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Navigation Example");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel cardPanel = new JPanel();
            CardLayout cardLayout = new CardLayout();
            cardPanel.setLayout(cardLayout);

            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.RED);
            cardPanel.add(panel1, "Panel 1");

            JPanel panel2 = new JPanel();
            panel2.setBackground(Color.BLUE);
            cardPanel.add(panel2, "Panel 2");

            JButton switchButton1 = new JButton("Switch to Panel 1");
            JButton switchButton2 = new JButton("Switch to Panel 2");

            NavigationManager navigationManager = new NavigationManager(frame, cardPanel, cardLayout);

            switchButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigationManager.switchTo("Panel 1");
                }
            });

            switchButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigationManager.switchTo("Panel 2");
                }
            });

            JPanel controlPanel = new JPanel();
            controlPanel.add(switchButton1);
            controlPanel.add(switchButton2);

            frame.add(cardPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}
