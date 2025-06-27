package com.fyp.ui;

import com.fyp.data.DataManager;
import com.fyp.model.Project;
import com.fyp.model.Review;

import javax.swing.*;
import java.awt.*;

public class ReviewDialog extends JDialog {
    private final DataManager dataManager;
    private final Project project;

    private final JSpinner technicalSpinner;
    private final JSpinner methodologySpinner;
    private final JSpinner objectivesSpinner;
    private final JSpinner outcomesSpinner;
    private final JTextArea commentsArea;
    private final JTextArea recommendationsArea;
    private final JCheckBox approveCheckBox;
    private final JButton submitButton;

    public ReviewDialog(DataManager dataManager, Project project) {
        super((Frame) null, "Review Proposal", true);
        this.dataManager = dataManager;
        this.project = project;
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(16, 16));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Technical Feasibility (1-5):"), gbc);
        gbc.gridx = 1;
        technicalSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        formPanel.add(technicalSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Methodology (1-5):"), gbc);
        gbc.gridx = 1;
        methodologySpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        formPanel.add(methodologySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Objectives (1-5):"), gbc);
        gbc.gridx = 1;
        objectivesSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        formPanel.add(objectivesSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Expected Outcomes (1-5):"), gbc);
        gbc.gridx = 1;
        outcomesSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        formPanel.add(outcomesSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Comments:"), gbc);
        gbc.gridx = 1;
        commentsArea = new JTextArea(3, 20);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(commentsArea), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Recommendations:"), gbc);
        gbc.gridx = 1;
        recommendationsArea = new JTextArea(3, 20);
        recommendationsArea.setLineWrap(true);
        recommendationsArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(recommendationsArea), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Approve Proposal:"), gbc);
        gbc.gridx = 1;
        approveCheckBox = new JCheckBox();
        formPanel.add(approveCheckBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit Review");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        Review review = new Review(project.getId(), ""); // ReviewerId can be set if login implemented
        review.setTechnicalFeasibility((Integer) technicalSpinner.getValue());
        review.setMethodology((Integer) methodologySpinner.getValue());
        review.setObjectives((Integer) objectivesSpinner.getValue());
        review.setExpectedOutcomes((Integer) outcomesSpinner.getValue());
        review.setComments(commentsArea.getText());
        review.setRecommendations(recommendationsArea.getText());
        review.setApproved(approveCheckBox.isSelected());
        review.calculateOverallRating();
        review.setStatus(Review.ReviewStatus.COMPLETED);
        dataManager.addReview(review);

        if (approveCheckBox.isSelected()) {
            project.setStatus(Project.ProjectStatus.APPROVED);
        } else {
            project.setStatus(Project.ProjectStatus.REJECTED);
        }
        dataManager.updateProject(project);

        JOptionPane.showMessageDialog(this, "Review submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}