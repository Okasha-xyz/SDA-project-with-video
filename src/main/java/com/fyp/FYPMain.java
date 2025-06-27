package com.fyp;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.fyp.data.DataManager;
import com.fyp.ui.MainFrame;

import javax.swing.*;

public class FYPMain {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("Starting FYP Management System...");
            DataManager dataManager = new DataManager();

            // Ensure we have sample data
            if (dataManager.getAllProjects().isEmpty()) {
                System.out.println("No projects found. Initializing sample data...");
                dataManager.initializeWithFakeData();
            }

            System.out.println("Sample data loaded:");
            System.out.println("- Projects: " + dataManager.getAllProjects().size());
            System.out.println("- Supervisions: " + dataManager.getAllSupervisions().size());
            System.out.println("- Reviews: " + dataManager.getAllReviews().size());

            MainFrame mainFrame = new MainFrame(dataManager);
            mainFrame.setVisible(true);
            System.out.println("FYP Management System is ready!");
        });
    }
}