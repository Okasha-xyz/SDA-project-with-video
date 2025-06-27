package com.fyp.model;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String department;
    private String specialization;

    public User(String name, String email, String password, UserRole role, String department, String specialization) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
        this.specialization = specialization;
    }

    // Default constructor for JSON deserialization
    public User() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }

    public enum UserRole {
        STUDENT("Student"),
        SUPERVISOR("Supervisor"),
        CO_SUPERVISOR("Co-Supervisor"),
        REVIEWER("Reviewer"),
        ADMIN("Administrator");

        private final String displayName;

        UserRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
} 