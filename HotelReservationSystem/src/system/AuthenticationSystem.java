package system;

import java.util.*;

public class AuthenticationSystem {
    
    // Singleton instance
    private static AuthenticationSystem instance;
    
    private HashMap<String, User> users = new HashMap<>();
    private User currentUser;
    
    // Private constructor to prevent direct instantiation
    AuthenticationSystem() {
        this.users = new HashMap<>();
        this.currentUser = null;
    }
    
    // Public method to get the singleton instance
    public static AuthenticationSystem getInstance() {
        if (instance == null) {
            instance = new AuthenticationSystem();
        }
        return instance;
    }
    
    public boolean registerUser(String name, String email, String password, String phoneNumber) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Registration failed: Name cannot be empty");
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Registration failed: Email cannot be empty");
            return false;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Registration failed: Password cannot be empty");
            return false;
        }
        
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Registration failed: Phone number cannot be empty");
            return false;
        }
        
        // Check if user already exists (using email as unique identifier)
        if (users.containsKey(email.toLowerCase())) {
            System.out.println("Registration failed: User with email " + email + " already exists");
            return false;
        }
        
        if (!isValidEmail(email)) {
            System.out.println("Registration failed: Invalid email format");
            return false;
        }
        
        if (password.length() < 6) {
            System.out.println("Registration failed: Password must be at least 6 characters");
            return false;
        }
        
        User newUser = new User(name.trim(), email.toLowerCase().trim(), password, phoneNumber.trim());
        users.put(email.toLowerCase(), newUser);
        
        System.out.println("Registration successful for user: " + name);
        return true;
    }
    
    public boolean login(String email, String password) {
        // Validate input
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Login failed: Email cannot be empty");
            return false;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Login failed: Password cannot be empty");
            return false;
        }
        
        // Check if user exists
        User user = users.get(email.toLowerCase().trim());
        if (user == null) {
            System.out.println("Login failed: User not found");
            return false;
        }
        
        // Verify password
        if (!user.getPassword().equals(password)) {
            System.out.println("Login failed: Incorrect password");
            return false;
        }
        
        // Set current user and log in
        currentUser = user;
        System.out.println("Login successful! Welcome, " + user.getName());
        return true;
    }
    
    public void logout() {
        if (currentUser != null) {
            System.out.println("User " + currentUser.getName() + " logged out successfully");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in");
        }
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Utility method for basic email validation
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public int getTotalUsers() {
        return users.size();
    }
    
    public boolean userExists(String email) {
        return users.containsKey(email.toLowerCase());
    }    
    
    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered in the system");
            return;
        }
        
        System.out.println("Registered Users:");
        System.out.println("-----------------");
        for (User user : users.values()) {
            System.out.println("Name: " + user.getName() + 
                             ", Email: " + user.getEmail() + 
                             ", Password: " + user.getPassword() +
                             ", Phone: " + user.getPhoneNumber());
        }
    }
}
