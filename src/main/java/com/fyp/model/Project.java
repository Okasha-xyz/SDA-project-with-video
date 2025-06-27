package com.fyp.model;

import java.time.LocalDate;
import java.util.UUID;

public class Project {
    private String id;
    private String title;
    private String description;
    private String objectives;
    private String methodology;
    private String expectedOutcomes;
    private String studentId;
    private String supervisorId;
    private String coSupervisorId;
    private String reviewerId;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String department;
    private String category;
    private double budget;
    private String requirements;
    private String deliverables;

    public Project(String title, String description, String studentId, String department) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.studentId = studentId;
        this.department = department;
        this.status = ProjectStatus.PROPOSED;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusMonths(6);
    }

    // Default constructor for JSON deserialization
    public Project() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getObjectives() { return objectives; }
    public void setObjectives(String objectives) { this.objectives = objectives; }

    public String getMethodology() { return methodology; }
    public void setMethodology(String methodology) { this.methodology = methodology; }

    public String getExpectedOutcomes() { return expectedOutcomes; }
    public void setExpectedOutcomes(String expectedOutcomes) { this.expectedOutcomes = expectedOutcomes; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSupervisorId() { return supervisorId; }
    public void setSupervisorId(String supervisorId) { this.supervisorId = supervisorId; }

    public String getCoSupervisorId() { return coSupervisorId; }
    public void setCoSupervisorId(String coSupervisorId) { this.coSupervisorId = coSupervisorId; }

    public String getReviewerId() { return reviewerId; }
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public String getDeliverables() { return deliverables; }
    public void setDeliverables(String deliverables) { this.deliverables = deliverables; }

    @Override
    public String toString() {
        return title + " (" + status + ")";
    }

    public enum ProjectStatus {
        PROPOSED("Proposed"),
        UNDER_REVIEW("Under Review"),
        APPROVED("Approved"),
        SUPERVISION_ACCEPTED("Supervision Accepted"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        REJECTED("Rejected");

        private final String displayName;

        ProjectStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
} 