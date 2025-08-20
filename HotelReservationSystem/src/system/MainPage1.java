package system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class MainPage1 extends JFrame {
    private AuthenticationSystem authSystem;
    private Hotel hotel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    
    // Color scheme
    private final Color BACKGROUND_COLOR = new Color(240, 235, 225);
    private final Color PANEL_COLOR = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(255, 165, 0);
    private final Color BUTTON_HOVER_COLOR = new Color(255, 140, 0);
    private final Color TEXT_COLOR = new Color(60, 60, 60);
    private final Color SUCCESS_COLOR = new Color(0, 150, 0);
    private final Color ERROR_COLOR = Color.RED;
    
    // UI Components for different panels
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable reservationsTable;
    private DefaultTableModel reservationsTableModel;
    private JLabel statusLabel;
    
    // Form fields for room booking
    private JTextField roomIdField, roomTypeField, priceField, capacityField, amenitiesField;
    private JDateChooser checkInDateChooser, checkOutDateChooser; // Changed to JDateChooser
    private JTextField guestsField;
    private JComboBox<String> availableRoomsCombo;

    public MainPage(AuthenticationSystem authSystem) {
        this.authSystem = authSystem;
        this.hotel = Hotel.getInstance();
        initializeGUI();
    }
    

    private void initializeGUI() {
        setTitle("Hotel Reservation System - " + hotel.getName());
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);
        
        // Create different panels
        JPanel menuPanel = createMenuPanel();
        JPanel roomsPanel = createRoomsPanel();
        JPanel bookingPanel = createBookingPanel();
        JPanel reservationsPanel = createReservationsPanel();
        JPanel addRoomPanel = createAddRoomPanel();
        
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(roomsPanel, "ROOMS");
        mainPanel.add(bookingPanel, "BOOKING");
        mainPanel.add(reservationsPanel, "RESERVATIONS");
        mainPanel.add(addRoomPanel, "ADD_ROOM");
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(SUCCESS_COLOR);
        statusLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Add components to frame
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        // Show menu panel by default
        cardLayout.show(mainPanel, "MENU");
        updateWelcomeMessage();
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 165, 0));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        // Hotel name and info
        JLabel hotelLabel = new JLabel(hotel.getName());
        hotelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        hotelLabel.setForeground(Color.WHITE);
        
        JLabel locationLabel = new JLabel(hotel.getLocation());
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setForeground(Color.WHITE);
        
        JPanel hotelInfoPanel = new JPanel(new BorderLayout());
        hotelInfoPanel.setOpaque(false);
        hotelInfoPanel.add(hotelLabel, BorderLayout.NORTH);
        hotelInfoPanel.add(locationLabel, BorderLayout.SOUTH);
        
        // Welcome message and logout
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(TEXT_COLOR);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(welcomeLabel, BorderLayout.NORTH);
        rightPanel.add(logoutButton, BorderLayout.SOUTH);
        
        headerPanel.add(hotelInfoPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(PANEL_COLOR);
        menuPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(40, 40, 40, 40)
        ));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(400, 500));
        
        // Title
        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createVerticalStrut(40));
        
        // Menu buttons
        String[] menuItems = {
            "View Available Rooms",
            "Make Reservation", 
            "View My Reservations",
            "Add New Room (Admin)"
        };
        
        String[] menuActions = {"ROOMS", "BOOKING", "RESERVATIONS", "ADD_ROOM"};
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createMenuButton(menuItems[i]);
            final String action = menuActions[i];
            menuButton.addActionListener(e -> {
                cardLayout.show(mainPanel, action);
                if ("ROOMS".equals(action)) {
                    refreshRoomsTable();
                } else if ("RESERVATIONS".equals(action)) {
                    refreshReservationsTable();
                } else if ("BOOKING".equals(action)) {
                    refreshAvailableRoomsCombo();
                }
            });
            menuPanel.add(menuButton);
            menuPanel.add(Box.createVerticalStrut(15));
        }
        
        outerPanel.add(menuPanel);
        return outerPanel;
    }
    
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Available Rooms");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JButton backButton = createStyledButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        
        // Rooms table
        String[] columnNames = {"Room ID", "Type", "Price/Night", "Capacity", "Amenities", "Status"};
        roomsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomsTable = new JTable(roomsTableModel);
        roomsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        roomsTable.setRowHeight(25);
        roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.setBackground(PANEL_COLOR);
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBookingPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);
        
        JPanel bookingPanel = new JPanel();
        bookingPanel.setBackground(PANEL_COLOR);
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));
        bookingPanel.setPreferredSize(new Dimension(400, 550)); // Increased height for date choosers
        
        // Title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel titleLabel = new JLabel("Make Reservation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JButton backButton = createLinkButton("← Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        bookingPanel.add(topPanel);
        bookingPanel.add(Box.createVerticalStrut(30));
        
        // Available rooms
        bookingPanel.add(createFieldLabel("Select Room:"));
        availableRoomsCombo = new JComboBox<>();
        availableRoomsCombo.setPreferredSize(new Dimension(340, 35));
        availableRoomsCombo.setMaximumSize(new Dimension(340, 35));
        availableRoomsCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingPanel.add(availableRoomsCombo);
        bookingPanel.add(Box.createVerticalStrut(15));
        
        // Check-in date
        bookingPanel.add(createFieldLabel("Check-in Date:"));
        checkInDateChooser = createDateChooser();
        bookingPanel.add(checkInDateChooser);
        bookingPanel.add(Box.createVerticalStrut(15));
        
        // Check-out date
        bookingPanel.add(createFieldLabel("Check-out Date:"));
        checkOutDateChooser = createDateChooser();
        bookingPanel.add(checkOutDateChooser);
        bookingPanel.add(Box.createVerticalStrut(15));
        
        // Number of guests
        bookingPanel.add(createFieldLabel("Number of Guests:"));
        guestsField = createTextField();
        bookingPanel.add(guestsField);
        bookingPanel.add(Box.createVerticalStrut(25));
        
        // Book button
        JButton bookButton = createStyledButton("Make Reservation");
        bookButton.addActionListener(new BookRoomAction());
        bookingPanel.add(bookButton);
        
        outerPanel.add(bookingPanel);
        return outerPanel;
    }
    
    private JPanel createReservationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("My Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JButton backButton = createStyledButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        
        // Reservations table
        String[] columnNames = {"Reservation ID", "Room", "Check-in", "Check-out", "Guests", "Total Price", "Status"};
        reservationsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reservationsTable = new JTable(reservationsTableModel);
        reservationsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        reservationsTable.setRowHeight(25);
        reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBackground(PANEL_COLOR);
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setOpaque(false);
        
        JButton cancelButton = createStyledButton("Cancel Reservation");
        cancelButton.setBackground(ERROR_COLOR);
        cancelButton.addActionListener(new CancelReservationAction());
        
        buttonsPanel.add(cancelButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createAddRoomPanel() {
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);
        
        JPanel addRoomPanel = new JPanel();
        addRoomPanel.setBackground(PANEL_COLOR);
        addRoomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        addRoomPanel.setLayout(new BoxLayout(addRoomPanel, BoxLayout.Y_AXIS));
        addRoomPanel.setPreferredSize(new Dimension(400, 500));
        
        // Title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel titleLabel = new JLabel("Add New Room");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        
        JButton backButton = createLinkButton("← Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        addRoomPanel.add(topPanel);
        addRoomPanel.add(Box.createVerticalStrut(30));
        
        // Form fields
        addRoomPanel.add(createFieldLabel("Room ID:"));
        roomIdField = createTextField();
        addRoomPanel.add(roomIdField);
        addRoomPanel.add(Box.createVerticalStrut(15));
        
        addRoomPanel.add(createFieldLabel("Room Type:"));
        roomTypeField = createTextField();
        addRoomPanel.add(roomTypeField);
        addRoomPanel.add(Box.createVerticalStrut(15));
        
        addRoomPanel.add(createFieldLabel("Price per Night:"));
        priceField = createTextField();
        addRoomPanel.add(priceField);
        addRoomPanel.add(Box.createVerticalStrut(15));
        
        addRoomPanel.add(createFieldLabel("Capacity:"));
        capacityField = createTextField();
        addRoomPanel.add(capacityField);
        addRoomPanel.add(Box.createVerticalStrut(15));
        
        addRoomPanel.add(createFieldLabel("Amenities:"));
        amenitiesField = createTextField();
        addRoomPanel.add(amenitiesField);
        addRoomPanel.add(Box.createVerticalStrut(25));
        
        // Add room button
        JButton addButton = createStyledButton("Add Room");
        addButton.addActionListener(new AddRoomAction());
        addRoomPanel.add(addButton);
        
        outerPanel.add(addRoomPanel);
        return outerPanel;
    }
    
    // Helper methods for creating UI components
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(340, 35));
        field.setMaximumSize(new Dimension(340, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    // New method to create date chooser
    private JDateChooser createDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(340, 35));
        dateChooser.setMaximumSize(new Dimension(340, 35));
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Set date format
        dateChooser.setDateFormatString("yyyy-MM-dd");
        
        // Set minimum date to today to prevent past dates
        dateChooser.setMinSelectableDate(new Date());
        
        // Style the date chooser
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(2, 5, 2, 5)
        ));
        
        return dateChooser;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(340, 40));
        button.setMaximumSize(new Dimension(340, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(ERROR_COLOR)) {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });
        
        return button;
    }
    
    private JButton createMenuButton(String text) {
        JButton button = createStyledButton(text);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }
    
    private JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setForeground(new Color(100, 100, 100));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(TEXT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(100, 100, 100));
            }
        });
        
        return button;
    }
    
    // Action classes
    private class BookRoomAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedRoom = (String) availableRoomsCombo.getSelectedItem();
                if (selectedRoom == null) {
                    showStatus("Please select a room.", ERROR_COLOR);
                    return;
                }
                
                String roomId = selectedRoom.split(" - ")[0];
                Room room = hotel.findRoomByNumber(roomId);
                
                if (room == null) {
                    showStatus("Selected room not found.", ERROR_COLOR);
                    return;
                }
                
                // Get dates from date choosers
                Date checkIn = checkInDateChooser.getDate();
                Date checkOut = checkOutDateChooser.getDate();
                
                if (checkIn == null || checkOut == null) {
                    showStatus("Please select both check-in and check-out dates.", ERROR_COLOR);
                    return;
                }
                
                // Additional date validation - normalize to start of day
                checkIn = normalizeToStartOfDay(checkIn);
                checkOut = normalizeToStartOfDay(checkOut);
                
                // Validate that check-out is after check-in (this is redundant since Hotel class also checks)
                if (!checkOut.after(checkIn)) {
                    showStatus("Check-out date must be after check-in date.", ERROR_COLOR);
                    return;
                }
                
                // Validate guests input
                String guestsText = guestsField.getText().trim();
                if (guestsText.isEmpty()) {
                    showStatus("Please enter the number of guests.", ERROR_COLOR);
                    return;
                }
                
                int guests = Integer.parseInt(guestsText);
                
                // Call hotel's makeReservation method
                boolean success = hotel.makeReservation(authSystem.getCurrentUser(), room, checkIn, checkOut, guests);
           
                if (success) {
                    showStatus("Reservation successful!", SUCCESS_COLOR);
                    
                    // Clear fields
                    checkInDateChooser.setDate(null);
                    checkOutDateChooser.setDate(null);
                    guestsField.setText("");
                    refreshRoomsTable();
                    refreshAvailableRoomsCombo();
                } else {
                    // Display the specific error message from the hotel
                    showStatus(hotel.getLastErrorMessage(), ERROR_COLOR);
                }
                
            } catch (NumberFormatException ex) {
                showStatus("Please enter a valid number of guests.", ERROR_COLOR);
            } catch (Exception ex) {
                showStatus("Error making reservation: " + ex.getMessage(), ERROR_COLOR);
                ex.printStackTrace(); // For debugging
            }
        }
        
        /**
         * Normalizes a date to the start of the day (00:00:00.000)
         * This helps with date comparisons by removing time components
         */
        private Date normalizeToStartOfDay(Date date) {
            if (date == null) return null;
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }
    
    private class AddRoomAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String roomId = roomIdField.getText().trim();
                String roomType = roomTypeField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int capacity = Integer.parseInt(capacityField.getText().trim());
                String amenities = amenitiesField.getText().trim();
                
                if (roomId.isEmpty() || roomType.isEmpty()) {
                    showStatus("Room ID and Type are required.", ERROR_COLOR);
                    return;
                }
                
                
                showStatus("Room added successfully!", SUCCESS_COLOR);
                
                // Clear fields
                roomIdField.setText("");
                roomTypeField.setText("");
                priceField.setText("");
                capacityField.setText("");
                amenitiesField.setText("");
                
            } catch (NumberFormatException ex) {
                showStatus("Invalid price or capacity.", ERROR_COLOR);
            } catch (Exception ex) {
                showStatus("Error adding room: " + ex.getMessage(), ERROR_COLOR);
            }
        }
    }
    
    private class CancelReservationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = reservationsTable.getSelectedRow();
            if (selectedRow == -1) {
                showStatus("Please select a reservation to cancel.", ERROR_COLOR);
                return;
            }
            
            String reservationId = (String) reservationsTableModel.getValueAt(selectedRow, 0);
            
            // Find and cancel the reservation
            List<Reservation> reservations = hotel.getReservations();
            for (Reservation reservation : reservations) {
                if (reservation.getReservationId().equals(reservationId)) {
                    reservation.cancelReservation();
                    showStatus("Reservation cancelled successfully!", SUCCESS_COLOR);
                    refreshReservationsTable();
                    break;
                }
            }
        }
    }
    
    // Utility methods
 // Replace the refreshRoomsTable method in MainPage.java with this:
    private void refreshRoomsTable() {
        roomsTableModel.setRowCount(0);
        List<Room> rooms = hotel.getAllRooms();
        
        for (Room room : rooms) {
            // Use the hotel's method to get proper room status
            String status = hotel.getRoomStatus(room);
            
            Object[] row = {
                room.getRoomId(),
                room.getRoomType(),
                "₱" + String.format("%.2f", room.getPrice()),
                room.getCapacity(),
                room.getAmenities(),
                status
            };
            roomsTableModel.addRow(row);
        }
    }
    
    private void refreshReservationsTable() {
        reservationsTableModel.setRowCount(0);
        List<Reservation> userReservations = hotel.getReservationsForUser(authSystem.getCurrentUser());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Reservation reservation : userReservations) {
            Object[] row = {
                reservation.getReservationId(),
                reservation.getRoom().getRoomId() + " (" + reservation.getRoom().getRoomType() + ")",
                sdf.format(reservation.getCheckInDate()),
                sdf.format(reservation.getCheckOutDate()),
                reservation.getNumberOfGuests(),
                "₱" + String.format("%.2f", reservation.getTotalPrice()),
                reservation.getStatus()
            };
            reservationsTableModel.addRow(row);
        }
    }
    
    private void refreshAvailableRoomsCombo() {
        availableRoomsCombo.removeAllItems();
        List<Room> availableRooms = hotel.getAvailableRooms();
        
        for (Room room : availableRooms) {
            String roomInfo = room.getRoomId() + " - " + room.getRoomType() + 
                            " (₱" + String.format("%.2f", room.getPrice()) + "/night) " + "Capacity: " + room.getCapacity(); 
            availableRoomsCombo.addItem(roomInfo);
        }
    }
    
    private void updateWelcomeMessage() {
        if (authSystem.getCurrentUser() != null) {
            welcomeLabel.setText("Welcome, " + authSystem.getCurrentUser().getName() + "!");
        }
    }
    
    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
        
        // Clear status after 5 seconds
        Timer timer = new Timer(5000, e -> statusLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    private void logout() {
        authSystem.logout();
        dispose();
        
        // Return to login screen with the SAME AuthenticationSystem instance
        SwingUtilities.invokeLater(() -> {
            Main loginGui = new Main(); // Pass the existing instance
            loginGui.setVisible(true);
        });
    }
}
