package com.fyp.ui;

import com.fyp.data.DataManager;
import com.fyp.model.Project;
import com.fyp.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReviewProposalPanel extends JPanel {
    private final DataManager dataManager;
    private final DefaultListModel<Project> projectListModel;
    private final JList<Project> projectJList;
    private final JTextArea detailsArea;
    private final JButton reviewButton;

    public ReviewProposalPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("Project Proposals for Review");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        projectListModel = new DefaultListModel<>();
        projectJList = new JList<>(projectListModel);
        projectJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectJList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane listScroll = new JScrollPane(projectJList);
        listScroll.setPreferredSize(new Dimension(320, 400));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setPreferredSize(new Dimension(400, 400));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        centerPanel.add(listScroll);
        centerPanel.add(detailsScroll);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        reviewButton = new JButton("Review Proposal");
        buttonPanel.add(refreshButton);
        buttonPanel.add(reviewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        reviewButton.setEnabled(false);

        projectJList.addListSelectionListener(e -> {
            Project selected = projectJList.getSelectedValue();
            if (selected != null) {
                detailsArea.setText(getProjectDetails(selected));
                reviewButton.setEnabled(selected.getStatus() == Project.ProjectStatus.UNDER_REVIEW);
            } else {
                detailsArea.setText("");
                reviewButton.setEnabled(false);
            }
        });

        refreshButton.addActionListener(e -> refreshProjectList());
        reviewButton.addActionListener(e -> handleReview());

        // Load data after a short delay to ensure DataManager is fully initialized
        SwingUtilities.invokeLater(() -> {
            refreshProjectList();
        });
    }

    private void refreshProjectList() {
        projectListModel.clear();
        List<Project> underReview = dataManager.getProjectsByStatus(Project.ProjectStatus.UNDER_REVIEW);
        for (Project p : underReview) {
            projectListModel.addElement(p);
        }
    }

    private String getProjectDetails(Project p) {
        User student = dataManager.getUserById(p.getStudentId());
        User supervisor = dataManager.getUserById(p.getSupervisorId());
        User reviewer = dataManager.getUserById(p.getReviewerId());

        StringBuilder details = new StringBuilder();
        details.append("=== PROJECT PROPOSAL ===\n\n");
        details.append("Title: ").append(p.getTitle()).append("\n");
        details.append("Status: ").append(p.getStatus().getDisplayName()).append("\n");
        details.append("Department: ").append(p.getDepartment()).append("\n\n");

        details.append("Student: ").append(student != null ? student.getName() : p.getStudentId()).append("\n");
        if (supervisor != null) {
            details.append("Supervisor: ").append(supervisor.getName()).append("\n");
        }
        if (reviewer != null) {
            details.append("Reviewer: ").append(reviewer.getName()).append("\n");
        }
        details.append("\n");

        details.append("=== DESCRIPTION ===\n");
        details.append(p.getDescription() != null ? p.getDescription() : "No description provided").append("\n\n");

        if (p.getObjectives() != null && !p.getObjectives().isEmpty()) {
            details.append("=== OBJECTIVES ===\n");
            details.append(p.getObjectives()).append("\n\n");
        }

        if (p.getMethodology() != null && !p.getMethodology().isEmpty()) {
            details.append("=== METHODOLOGY ===\n");
            details.append(p.getMethodology()).append("\n\n");
        }

        if (p.getExpectedOutcomes() != null && !p.getExpectedOutcomes().isEmpty()) {
            details.append("=== EXPECTED OUTCOMES ===\n");
            details.append(p.getExpectedOutcomes()).append("\n\n");
        }

        details.append("Start Date: ").append(p.getStartDate()).append("\n");
        details.append("End Date: ").append(p.getEndDate()).append("\n");

        return details.toString();
    }

    private void handleReview() {
        Project selected = projectJList.getSelectedValue();
        if (selected != null && selected.getStatus() == Project.ProjectStatus.UNDER_REVIEW) {
            ReviewDialog dialog = new ReviewDialog(dataManager, selected);
            dialog.setVisible(true);
            refreshProjectList();
            detailsArea.setText(selected != null ? getProjectDetails(selected) : "");
        }
    }
}