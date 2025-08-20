package system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private static AuthenticationSystem authSystem;
    private JTextField regUsernameField, regEmailField, regPhoneNumberField;
    private JPasswordField regPasswordField;
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JLabel statusLabel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Color scheme
    private final Color BACKGROUND_COLOR = new Color(240, 235, 225);
    private final Color PANEL_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(255, 165, 0);
    private final Color BUTTON_HOVER_COLOR = new Color(255, 140, 0);
    private final Color TEXT_COLOR = new Color(60, 60, 60);
    private final Color LINK_COLOR = new Color(100, 100, 100);

    public Main() {
        authSystem = new AuthenticationSystem();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Authentication System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set background color
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        // Create login and registration panels
        JPanel loginPanel = createLoginPanel();
        JPanel registrationPanel = createRegistrationPanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registrationPanel, "REGISTER");

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);
        statusLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Add components to frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Show login panel by default
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);

        // Create the login form panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(PANEL_COLOR);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(40, 40, 40, 40)));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setPreferredSize(new Dimension(300, 400));

        // Sign up link at top right
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        JButton signUpLink = createLinkButton("Sign up");
        signUpLink.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));
        topPanel.add(signUpLink);
        loginPanel.add(topPanel);

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(30));

        // Email field
        loginPanel.add(createFieldLabel("Email *"));
        loginEmailField = createTextField();
        loginPanel.add(loginEmailField);
        loginPanel.add(Box.createVerticalStrut(15));

        // Password field
        loginPanel.add(createFieldLabel("Password *"));
        loginPasswordField = createPasswordField();
        loginPanel.add(loginPasswordField);
        loginPanel.add(Box.createVerticalStrut(25));

        // Login button
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(new LoginAction());
        loginPanel.add(loginButton);

        outerPanel.add(loginPanel);
        return outerPanel;
    }

    private JPanel createRegistrationPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);

        // Create the registration form panel
        JPanel regPanel = new JPanel();
        regPanel.setBackground(PANEL_COLOR);
        regPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(40, 40, 40, 40)));
        regPanel.setLayout(new BoxLayout(regPanel, BoxLayout.Y_AXIS));
        regPanel.setPreferredSize(new Dimension(300, 500));

        // Login link at top right
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        JButton loginLink = createLinkButton("Login");
        loginLink.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        topPanel.add(loginLink);
        regPanel.add(topPanel);

        // Title
        JLabel titleLabel = new JLabel("Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        regPanel.add(titleLabel);
        regPanel.add(Box.createVerticalStrut(30));

        // Email field
        regPanel.add(createFieldLabel("Username *"));
        regUsernameField = createTextField();
        regPanel.add(regUsernameField);
        regPanel.add(Box.createVerticalStrut(15));

        // Email field
        regPanel.add(createFieldLabel("Email *"));
        regEmailField = createTextField();
        regPanel.add(regEmailField);
        regPanel.add(Box.createVerticalStrut(15));

        // Password field
        regPanel.add(createFieldLabel("Password *"));
        regPasswordField = createPasswordField();
        regPanel.add(regPasswordField);
        regPanel.add(Box.createVerticalStrut(15));

        // Confirm Password field
        regPanel.add(createFieldLabel("Phone Number *"));
        regPhoneNumberField = createTextField();
        regPanel.add(regPhoneNumberField);
        regPanel.add(Box.createVerticalStrut(25));

        // Sign up button
        JButton signUpButton = createStyledButton("Sign up");
        signUpButton.addActionListener(new RegisterAction());
        regPanel.add(signUpButton);

        outerPanel.add(regPanel);
        return outerPanel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(220, 35));
        field.setMaximumSize(new Dimension(220, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(220, 35));
        field.setMaximumSize(new Dimension(220, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)));
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(220, 40));
        button.setMaximumSize(new Dimension(220, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setForeground(LINK_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(TEXT_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(LINK_COLOR);
            }
        });

        return button;
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = regUsernameField.getText().trim();
            String email = regEmailField.getText().trim();
            String password = new String(regPasswordField.getPassword());
            String phoneNumber = new String(regPhoneNumberField.getText().trim());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                statusLabel.setText("All fields are required.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            // sends the variable as arguments to the registerUser method in Authentication
            // System
            if (authSystem.registerUser(username, email, password, phoneNumber)) {
                statusLabel.setText("Registration successful! Please login.");
                authSystem.displayAllUsers();
                statusLabel.setForeground(new Color(0, 150, 0));
                // Clear fields
                regUsernameField.setText("");
                regEmailField.setText("");
                regPasswordField.setText("");
                regPhoneNumberField.setText("");
                // Switch to login panel
                cardLayout.show(mainPanel, "LOGIN");
            } else {
                statusLabel.setText("Registration failed: Email already in use or invalid input.");
                statusLabel.setForeground(Color.RED);
            }
        }
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = loginEmailField.getText().trim();
            String password = new String(loginPasswordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter both username and password.");
                statusLabel.setForeground(Color.RED);
                return;
            }

            // Login
            if (authSystem.login(email, password)) {
                statusLabel.setText("Login successful! Welcome, " + authSystem.getCurrentUser().getName());
                statusLabel.setForeground(new Color(0, 150, 0));
                // Clear password field
                loginPasswordField.setText("");
            } else {
                statusLabel.setText("Login failed: Invalid credentials.");
                statusLabel.setForeground(Color.RED);
            }
        }
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            // Use default look and feel if system look and feel is not available
        }

        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
            Main gui = new Main();
            gui.setVisible(true);
        });
    }
}
