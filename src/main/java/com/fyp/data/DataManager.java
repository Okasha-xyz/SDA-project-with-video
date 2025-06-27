package com.fyp.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fyp.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "users.json";
    private static final String PROJECTS_FILE = "projects.json";
    private static final String REVIEWS_FILE = "reviews.json";
    private static final String SUPERVISIONS_FILE = "supervisions.json";

    private final ObjectMapper objectMapper;
    private List<User> users;
    private List<Project> projects;
    private List<Review> reviews;
    private List<Supervision> supervisions;

    public DataManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        loadData();
    }

    private void loadData() {
        try {
            // Load users
            File usersFile = new File(DATA_DIR, USERS_FILE);
            if (usersFile.exists()) {
                users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {
                });
            } else {
                users = new ArrayList<>();
                createDefaultUsers();
            }

            // Load projects
            File projectsFile = new File(DATA_DIR, PROJECTS_FILE);
            if (projectsFile.exists()) {
                projects = objectMapper.readValue(projectsFile, new TypeReference<List<Project>>() {
                });
            } else {
                projects = new ArrayList<>();
            }

            // Load reviews
            File reviewsFile = new File(DATA_DIR, REVIEWS_FILE);
            if (reviewsFile.exists()) {
                reviews = objectMapper.readValue(reviewsFile, new TypeReference<List<Review>>() {
                });
            } else {
                reviews = new ArrayList<>();
            }

            // Load supervisions
            File supervisionsFile = new File(DATA_DIR, SUPERVISIONS_FILE);
            if (supervisionsFile.exists()) {
                supervisions = objectMapper.readValue(supervisionsFile, new TypeReference<List<Supervision>>() {
                });
            } else {
                supervisions = new ArrayList<>();
            }

            // If no fake data exists, create it
            if (projects.isEmpty() || supervisions.isEmpty()) {
                createFakeData();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Initialize with empty lists if loading fails
            users = new ArrayList<>();
            projects = new ArrayList<>();
            reviews = new ArrayList<>();
            supervisions = new ArrayList<>();
            createDefaultUsers();
        }
    }

    private void createDefaultUsers() {
        // Create default admin user
        users.add(new User("Admin", "admin@fyp.com", "admin123", User.UserRole.ADMIN, "IT", "System Administration"));

        // Create some default supervisors
        users.add(new User("Dr. Sarah Johnson", "sarah.johnson@university.edu", "password123", User.UserRole.SUPERVISOR,
                "Computer Science", "Machine Learning"));
        users.add(new User("Dr. Michael Chen", "michael.chen@university.edu", "password123", User.UserRole.SUPERVISOR,
                "Computer Science", "Software Engineering"));
        users.add(new User("Dr. Emily Davis", "emily.davis@university.edu", "password123", User.UserRole.SUPERVISOR,
                "Computer Science", "Database Systems"));

        // Create some default reviewers
        users.add(new User("Dr. Robert Wilson", "robert.wilson@university.edu", "password123", User.UserRole.REVIEWER,
                "Computer Science", "Artificial Intelligence"));
        users.add(new User("Dr. Lisa Brown", "lisa.brown@university.edu", "password123", User.UserRole.REVIEWER,
                "Computer Science", "Computer Networks"));

        // Create some default students
        users.add(new User("John Smith", "john.smith@student.edu", "password123", User.UserRole.STUDENT,
                "Computer Science", "Software Engineering"));
        users.add(new User("Alice Johnson", "alice.johnson@student.edu", "password123", User.UserRole.STUDENT,
                "Computer Science", "Data Science"));
        users.add(new User("Bob Wilson", "bob.wilson@student.edu", "password123", User.UserRole.STUDENT,
                "Computer Science", "Web Development"));

        saveUsers();
        createFakeData();
    }

    private void createFakeData() {
        // Get user IDs for creating relationships
        User student1 = getUserByEmail("john.smith@student.edu");
        User student2 = getUserByEmail("alice.johnson@student.edu");
        User student3 = getUserByEmail("bob.wilson@student.edu");
        User supervisor1 = getUserByEmail("sarah.johnson@university.edu");
        User supervisor2 = getUserByEmail("michael.chen@university.edu");
        User supervisor3 = getUserByEmail("emily.davis@university.edu");
        User reviewer1 = getUserByEmail("robert.wilson@university.edu");
        User reviewer2 = getUserByEmail("lisa.brown@university.edu");

        System.out.println("Creating fake data...");
        System.out.println(
                "Students found: " + (student1 != null) + ", " + (student2 != null) + ", " + (student3 != null));
        System.out.println("Supervisors found: " + (supervisor1 != null) + ", " + (supervisor2 != null) + ", "
                + (supervisor3 != null));
        System.out.println("Reviewers found: " + (reviewer1 != null) + ", " + (reviewer2 != null));

        // Create sample projects
        Project project1 = new Project(
                "AI-Powered Student Performance Prediction System",
                "A machine learning system to predict student academic performance based on various factors",
                student1.getId(),
                "Computer Science");
        project1.setObjectives("Develop an accurate prediction model for student performance");
        project1.setMethodology("Machine Learning, Data Analysis, Statistical Modeling");
        project1.setExpectedOutcomes("A working prediction system with 85% accuracy");
        project1.setStatus(Project.ProjectStatus.UNDER_REVIEW);
        project1.setSupervisorId(supervisor1.getId());
        project1.setReviewerId(reviewer1.getId());
        projects.add(project1);

        Project project2 = new Project(
                "Blockchain-Based Academic Credential Verification",
                "A decentralized system for verifying academic credentials using blockchain technology",
                student2.getId(),
                "Computer Science");
        project2.setObjectives("Create a secure and tamper-proof credential verification system");
        project2.setMethodology("Blockchain Development, Smart Contracts, Web3");
        project2.setExpectedOutcomes("A functional blockchain credential verification platform");
        project2.setStatus(Project.ProjectStatus.UNDER_REVIEW);
        project2.setSupervisorId(supervisor2.getId());
        project2.setReviewerId(reviewer2.getId());
        projects.add(project2);

        Project project3 = new Project(
                "IoT-Based Smart Campus Management System",
                "An Internet of Things system for managing campus facilities and resources",
                student3.getId(),
                "Computer Science");
        project3.setObjectives("Develop a comprehensive IoT system for campus management");
        project3.setMethodology("IoT Development, Sensor Networks, Cloud Computing");
        project3.setExpectedOutcomes("A complete smart campus management solution");
        project3.setStatus(Project.ProjectStatus.PROPOSED);
        projects.add(project3);

        System.out.println("Created " + projects.size() + " projects");

        // Create sample supervisions
        Supervision supervision1 = new Supervision(
                project1.getId(),
                supervisor1.getId(),
                student1.getId(),
                Supervision.SupervisionType.PRIMARY);
        supervision1.setRequestMessage(
                "I would like to request supervision for my AI project. I have experience in machine learning and believe this project will contribute significantly to the field.");
        supervision1.setStatus(Supervision.SupervisionStatus.PENDING);
        supervisions.add(supervision1);

        Supervision supervision2 = new Supervision(
                project2.getId(),
                supervisor2.getId(),
                student2.getId(),
                Supervision.SupervisionType.PRIMARY);
        supervision2.setRequestMessage(
                "Requesting supervision for my blockchain project. I have completed relevant courses and have a strong foundation in distributed systems.");
        supervision2.setStatus(Supervision.SupervisionStatus.PENDING);
        supervisions.add(supervision2);

        Supervision supervision3 = new Supervision(
                project3.getId(),
                supervisor3.getId(),
                student3.getId(),
                Supervision.SupervisionType.PRIMARY);
        supervision3.setRequestMessage(
                "I am interested in IoT and would like to work on a smart campus project. Please consider supervising my work.");
        supervision3.setStatus(Supervision.SupervisionStatus.PENDING);
        supervisions.add(supervision3);

        System.out.println("Created " + supervisions.size() + " supervisions");

        // Create sample reviews
        Review review1 = new Review(project1.getId(), reviewer1.getId());
        review1.setStatus(Review.ReviewStatus.PENDING);
        reviews.add(review1);

        Review review2 = new Review(project2.getId(), reviewer2.getId());
        review2.setStatus(Review.ReviewStatus.PENDING);
        reviews.add(review2);

        System.out.println("Created " + reviews.size() + " reviews");

        saveProjects();
        saveSupervisions();
        saveReviews();

        System.out.println("Fake data creation completed!");
    }

    public void saveData() {
        saveUsers();
        saveProjects();
        saveReviews();
        saveSupervisions();
    }

    private void saveUsers() {
        try {
            objectMapper.writeValue(new File(DATA_DIR, USERS_FILE), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProjects() {
        try {
            objectMapper.writeValue(new File(DATA_DIR, PROJECTS_FILE), projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveReviews() {
        try {
            objectMapper.writeValue(new File(DATA_DIR, REVIEWS_FILE), reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSupervisions() {
        try {
            objectMapper.writeValue(new File(DATA_DIR, SUPERVISIONS_FILE), supervisions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // User management methods
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User getUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public User getUserByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void updateUser(User user) {
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        saveUsers();
    }

    public List<User> getUsersByRole(User.UserRole role) {
        return users.stream().filter(u -> u.getRole() == role).collect(Collectors.toList());
    }

    // Project management methods
    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }

    public Project getProjectById(String id) {
        return projects.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void addProject(Project project) {
        projects.add(project);
        saveProjects();
    }

    public void updateProject(Project project) {
        projects.removeIf(p -> p.getId().equals(project.getId()));
        projects.add(project);
        saveProjects();
    }

    public List<Project> getProjectsByStatus(Project.ProjectStatus status) {
        return projects.stream().filter(p -> p.getStatus() == status).collect(Collectors.toList());
    }

    public List<Project> getProjectsByStudent(String studentId) {
        return projects.stream().filter(p -> p.getStudentId().equals(studentId)).collect(Collectors.toList());
    }

    public List<Project> getProjectsBySupervisor(String supervisorId) {
        return projects.stream().filter(p -> p.getSupervisorId().equals(supervisorId)).collect(Collectors.toList());
    }

    // Review management methods
    public List<Review> getAllReviews() {
        return new ArrayList<>(reviews);
    }

    public Review getReviewById(String id) {
        return reviews.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    public void addReview(Review review) {
        reviews.add(review);
        saveReviews();
    }

    public void updateReview(Review review) {
        reviews.removeIf(r -> r.getId().equals(review.getId()));
        reviews.add(review);
        saveReviews();
    }

    public List<Review> getReviewsByProject(String projectId) {
        return reviews.stream().filter(r -> r.getProjectId().equals(projectId)).collect(Collectors.toList());
    }

    public List<Review> getReviewsByReviewer(String reviewerId) {
        return reviews.stream().filter(r -> r.getReviewerId().equals(reviewerId)).collect(Collectors.toList());
    }

    // Supervision management methods
    public List<Supervision> getAllSupervisions() {
        return new ArrayList<>(supervisions);
    }

    public Supervision getSupervisionById(String id) {
        return supervisions.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public void addSupervision(Supervision supervision) {
        supervisions.add(supervision);
        saveSupervisions();
    }

    public void updateSupervision(Supervision supervision) {
        supervisions.removeIf(s -> s.getId().equals(supervision.getId()));
        supervisions.add(supervision);
        saveSupervisions();
    }

    public List<Supervision> getSupervisionsBySupervisor(String supervisorId) {
        return supervisions.stream().filter(s -> s.getSupervisorId().equals(supervisorId)).collect(Collectors.toList());
    }

    public List<Supervision> getSupervisionsByStudent(String studentId) {
        return supervisions.stream().filter(s -> s.getStudentId().equals(studentId)).collect(Collectors.toList());
    }

    public List<Supervision> getSupervisionsByStatus(Supervision.SupervisionStatus status) {
        return supervisions.stream().filter(s -> s.getStatus() == status).collect(Collectors.toList());
    }

    public void initializeWithFakeData() {
        // Clear existing data and create fresh fake data
        projects.clear();
        supervisions.clear();
        reviews.clear();
        createFakeData();
    }
}