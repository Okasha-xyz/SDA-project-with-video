package com.fyp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private String id;
    private String projectId;
    private String reviewerId;
    private LocalDateTime reviewDate;
    private ReviewStatus status;
    private int technicalFeasibility;
    private int methodology;
    private int objectives;
    private int expectedOutcomes;
    private int overallRating;
    private String comments;
    private String recommendations;
    private boolean approved;

    public Review(String projectId, String reviewerId) {
        this.id = UUID.randomUUID().toString();
        this.projectId = projectId;
        this.reviewerId = reviewerId;
        this.reviewDate = LocalDateTime.now();
        this.status = ReviewStatus.PENDING;
    }

    // Default constructor for JSON deserialization
    public Review() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getReviewerId() { return reviewerId; }
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }

    public ReviewStatus getStatus() { return status; }
    public void setStatus(ReviewStatus status) { this.status = status; }

    public int getTechnicalFeasibility() { return technicalFeasibility; }
    public void setTechnicalFeasibility(int technicalFeasibility) { this.technicalFeasibility = technicalFeasibility; }

    public int getMethodology() { return methodology; }
    public void setMethodology(int methodology) { this.methodology = methodology; }

    public int getObjectives() { return objectives; }
    public void setObjectives(int objectives) { this.objectives = objectives; }

    public int getExpectedOutcomes() { return expectedOutcomes; }
    public void setExpectedOutcomes(int expectedOutcomes) { this.expectedOutcomes = expectedOutcomes; }

    public int getOverallRating() { return overallRating; }
    public void setOverallRating(int overallRating) { this.overallRating = overallRating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public void calculateOverallRating() {
        this.overallRating = (technicalFeasibility + methodology + objectives + expectedOutcomes) / 4;
    }

    public String getRatingDescription() {
        if (overallRating >= 4) return "Excellent";
        else if (overallRating >= 3) return "Good";
        else if (overallRating >= 2) return "Fair";
        else return "Poor";
    }

    public enum ReviewStatus {
        PENDING("Pending"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed");

        private final String displayName;

        ReviewStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
} 