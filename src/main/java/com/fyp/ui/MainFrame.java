package com.fyp.ui;

import com.fyp.data.DataManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final DataManager dataManager;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainFrame(DataManager dataManager) {
        super("FYP Management System");
        this.dataManager = dataManager;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Navigation bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSupervision = new JButton("Accept Supervision");
        JButton btnReview = new JButton("Review Proposal");
        JButton btnInitData = new JButton("Initialize Sample Data");
        navBar.add(btnSupervision);
        navBar.add(btnReview);
        navBar.add(btnInitData);
        add(navBar, BorderLayout.NORTH);

        // Panels for use cases
        AcceptSupervisionPanel supervisionPanel = new AcceptSupervisionPanel(dataManager);
        ReviewProposalPanel reviewPanel = new ReviewProposalPanel(dataManager);
        mainPanel.add(supervisionPanel, "supervision");
        mainPanel.add(reviewPanel, "review");
        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        btnSupervision.addActionListener(e -> cardLayout.show(mainPanel, "supervision"));
        btnReview.addActionListener(e -> cardLayout.show(mainPanel, "review"));
        btnInitData.addActionListener(e -> {
            dataManager.initializeWithFakeData();
            JOptionPane.showMessageDialog(this, "Sample data initialized! Switch to panels to see the data.",
                    "Data Initialized", JOptionPane.INFORMATION_MESSAGE);
        });

        // Show default panel
        cardLayout.show(mainPanel, "supervision");
    }
}