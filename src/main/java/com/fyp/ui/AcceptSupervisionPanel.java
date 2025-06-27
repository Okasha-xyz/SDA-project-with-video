package com.fyp.ui;

import com.fyp.data.DataManager;
import com.fyp.model.Supervision;
import com.fyp.model.User;
import com.fyp.model.Project;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AcceptSupervisionPanel extends JPanel {
    private final DataManager dataManager;
    private final DefaultListModel<Supervision> supervisionListModel;
    private final JList<Supervision> supervisionJList;
    private final JTextArea detailsArea;
    private final JButton acceptButton;
    private final JButton rejectButton;

    public AcceptSupervisionPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("Pending Supervision Requests");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        supervisionListModel = new DefaultListModel<>();
        supervisionJList = new JList<>(supervisionListModel);
        supervisionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supervisionJList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane listScroll = new JScrollPane(supervisionJList);
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
        acceptButton = new JButton("Accept");
        rejectButton = new JButton("Reject");
        buttonPanel.add(refreshButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        acceptButton.setEnabled(false);
        rejectButton.setEnabled(false);

        supervisionJList.addListSelectionListener(e -> {
            Supervision selected = supervisionJList.getSelectedValue();
            if (selected != null) {
                detailsArea.setText(getSupervisionDetails(selected));
                acceptButton.setEnabled(selected.getStatus() == Supervision.SupervisionStatus.PENDING);
                rejectButton.setEnabled(selected.getStatus() == Supervision.SupervisionStatus.PENDING);
            } else {
                detailsArea.setText("");
                acceptButton.setEnabled(false);
                rejectButton.setEnabled(false);
            }
        });

        refreshButton.addActionListener(e -> refreshSupervisionList());
        acceptButton.addActionListener(e -> handleAccept());
        rejectButton.addActionListener(e -> handleReject());

        // Load data after a short delay to ensure DataManager is fully initialized
        SwingUtilities.invokeLater(() -> {
            refreshSupervisionList();
        });
    }

    private void refreshSupervisionList() {
        supervisionListModel.clear();
        List<Supervision> pending = dataManager.getSupervisionsByStatus(Supervision.SupervisionStatus.PENDING);
        for (Supervision s : pending) {
            supervisionListModel.addElement(s);
        }
    }

    private String getSupervisionDetails(Supervision s) {
        User supervisor = dataManager.getUserById(s.getSupervisorId());
        User student = dataManager.getUserById(s.getStudentId());
        Project project = dataManager.getProjectById(s.getProjectId());

        StringBuilder details = new StringBuilder();
        details.append("=== SUPERVISION REQUEST ===\n\n");
        details.append("Project: ").append(project != null ? project.getTitle() : "Unknown Project").append("\n");
        details.append("Project ID: ").append(s.getProjectId()).append("\n\n");
        details.append("Supervisor: ").append(supervisor != null ? supervisor.getName() : s.getSupervisorId())
                .append("\n");
        details.append("Student: ").append(student != null ? student.getName() : s.getStudentId()).append("\n");
        details.append("Type: ").append(s.getType().getDisplayName()).append("\n");
        details.append("Status: ").append(s.getStatus().getDisplayName()).append("\n");
        details.append("Request Date: ").append(s.getRequestDate()).append("\n\n");
        details.append("=== REQUEST MESSAGE ===\n");
        details.append(s.getRequestMessage() != null ? s.getRequestMessage() : "No message provided").append("\n\n");

        if (s.getResponseMessage() != null && !s.getResponseMessage().isEmpty()) {
            details.append("=== RESPONSE MESSAGE ===\n");
            details.append(s.getResponseMessage()).append("\n");
        }

        return details.toString();
    }

    private void handleAccept() {
        Supervision selected = supervisionJList.getSelectedValue();
        if (selected != null && selected.getStatus() == Supervision.SupervisionStatus.PENDING) {
            String response = JOptionPane.showInputDialog(this, "Enter acceptance message:", "Accept Supervision",
                    JOptionPane.PLAIN_MESSAGE);
            if (response != null) {
                selected.accept(response);
                dataManager.updateSupervision(selected);
                refreshSupervisionList();
                detailsArea.setText(getSupervisionDetails(selected));
                JOptionPane.showMessageDialog(this, "Supervision accepted!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void handleReject() {
        Supervision selected = supervisionJList.getSelectedValue();
        if (selected != null && selected.getStatus() == Supervision.SupervisionStatus.PENDING) {
            String response = JOptionPane.showInputDialog(this, "Enter rejection message:", "Reject Supervision",
                    JOptionPane.PLAIN_MESSAGE);
            if (response != null) {
                selected.reject(response);
                dataManager.updateSupervision(selected);
                refreshSupervisionList();
                detailsArea.setText(getSupervisionDetails(selected));
                JOptionPane.showMessageDialog(this, "Supervision rejected!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}