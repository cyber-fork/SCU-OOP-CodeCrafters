package com.fasttracklogistics;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        // Ensure GUI runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set a modern look and feel
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                System.out.println("⚠️ Failed to set look and feel. Using default.");
            }
            
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
