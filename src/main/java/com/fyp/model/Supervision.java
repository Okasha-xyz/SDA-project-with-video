package com.fyp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Supervision {
    private String id;
    private String projectId;
    private String supervisorId;
    private String studentId;
    private SupervisionType type;
    private SupervisionStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime responseDate;
    private String requestMessage;
    private String responseMessage;
    private boolean accepted;

    public Supervision(String projectId, String supervisorId, String studentId, SupervisionType type) {
        this.id = UUID.randomUUID().toString();
        this.projectId = projectId;
        this.supervisorId = supervisorId;
        this.studentId = studentId;
        this.type = type;
        this.status = SupervisionStatus.PENDING;
        this.requestDate = LocalDateTime.now();
    }

    // Default constructor for JSON deserialization
    public Supervision() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getSupervisorId() { return supervisorId; }
    public void setSupervisorId(String supervisorId) { this.supervisorId = supervisorId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public SupervisionType getType() { return type; }
    public void setType(SupervisionType type) { this.type = type; }

    public SupervisionStatus getStatus() { return status; }
    public void setStatus(SupervisionStatus status) { this.status = status; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }

    public LocalDateTime getResponseDate() { return responseDate; }
    public void setResponseDate(LocalDateTime responseDate) { this.responseDate = responseDate; }

    public String getRequestMessage() { return requestMessage; }
    public void setRequestMessage(String requestMessage) { this.requestMessage = requestMessage; }

    public String getResponseMessage() { return responseMessage; }
    public void setResponseMessage(String responseMessage) { this.responseMessage = responseMessage; }

    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public void accept(String responseMessage) {
        this.status = SupervisionStatus.ACCEPTED;
        this.accepted = true;
        this.responseDate = LocalDateTime.now();
        this.responseMessage = responseMessage;
    }

    public void reject(String responseMessage) {
        this.status = SupervisionStatus.REJECTED;
        this.accepted = false;
        this.responseDate = LocalDateTime.now();
        this.responseMessage = responseMessage;
    }

    public enum SupervisionType {
        PRIMARY("Primary Supervisor"),
        CO_SUPERVISOR("Co-Supervisor");

        private final String displayName;

        SupervisionType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum SupervisionStatus {
        PENDING("Pending"),
        ACCEPTED("Accepted"),
        REJECTED("Rejected");

        private final String displayName;

        SupervisionStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
} 