package system;

import java.util.*;

public class Hotel {
    private String hotelId;
    private String name;
    private String location;
    private String lastErrorMessage;
    
    private static Hotel Instance;
    private static List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations;

    
    
    // Default constructor
    Hotel() {
        Hotel.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Parameterized constructor
    public Hotel(String hotelId, String name, String location) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }
    
    public static Hotel getInstance() {
        if (Instance == null) {
            Instance = new Hotel();
            rooms.add(new Room("101", "Standard", 2500.0, 2, "WiFi, TV, AC"));
            rooms.add(new Room("102", "Standard", 2500.0, 2, "WiFi, TV, AC"));
            rooms.add(new Room("201", "Deluxe", 4000.0, 4, "WiFi, TV, AC, Mini Bar"));
            rooms.add(new Room("202", "Deluxe", 4000.0, 4, "WiFi, TV, AC, Mini Bar"));
            rooms.add(new Room("301", "Suite", 6500.0, 6, "WiFi, TV, AC, Mini Bar, Kitchenette"));
            rooms.add(new Room("302", "Suite", 6500.0, 6, "WiFi, TV, AC, Mini Bar, Kitchenette"));
        
        }
        return Instance;
    }

    public boolean hasActiveReservations(Room room) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomId().equals(room.getRoomId()) && 
                !reservation.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        
        for (Room room : rooms) {
            // Only add rooms that are available and can accept new reservations
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        
        return availableRooms;
    }

  
    public String getRoomStatus(Room room) {
        if (!room.isAvailable()) {
            return "Not Available";
        } else if (hasActiveReservations(room)) {
            return "Available except Reserved Dates";
        } else {
            return "Available";
        }
    }
    
    public String getLastErrorMessage() {
    	return lastErrorMessage;
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

    public boolean makeReservation(User user, Room room, java.util.Date checkInDate, 
                               java.util.Date checkOutDate, int numberOfGuests) {

        // Normalize dates to start of day for consistent comparison
        checkInDate = normalizeToStartOfDay(checkInDate);
        checkOutDate = normalizeToStartOfDay(checkOutDate);
        Date today = normalizeToStartOfDay(new Date());
    	
        // Validate inputs
        if (user == null) {
            lastErrorMessage = "Reservation failed: User cannot be null";
            return false;
        }
        
        if (room == null) {
        	lastErrorMessage = "Reservation failed: Room cannot be null";
            return false;
        }
        
        if (checkInDate == null || checkOutDate == null) {
        	lastErrorMessage = "Reservation failed: Check-in and check-out dates cannot be null";
            return false;
        }
        
        if (checkInDate.after(checkOutDate)) {
        	lastErrorMessage = "Reservation failed: Check-in date must be before check-out date";
            return false;
        }
        
        // Use normalized dates for comparison
        if (checkInDate.before(today)) {
        	lastErrorMessage = "Reservation failed: Check-in date cannot be in the past";
            return false;
        }
        
        if (checkOutDate.equals(checkInDate)) {
        	lastErrorMessage = "Reservation failed: Check-out date must be at least one day after check-in";
            return false;   
        }
        
        if (numberOfGuests <= 0) {
        	lastErrorMessage = "Reservation failed: Number of guests must be positive";
            return false;
        }
        
        if (numberOfGuests > room.getCapacity()) {
        	lastErrorMessage = "Reservation failed: Number of guests (" + numberOfGuests + 
                              ") exceeds room capacity (" + room.getCapacity() + ")";
            return false;
        }
        
        // Check if room exists in this hotel
        if (!rooms.contains(room)) {
        	lastErrorMessage = "Reservation failed: Room not found in this hotel";
            return false;
        }
        
        // Check if room is available for the given dates
        if (!isRoomAvailableForDates(room, checkInDate, checkOutDate)) {
        	lastErrorMessage = "Reservation failed: Room is not available for the selected dates";
            return false;
        }
        
        // Create and add reservation
        String reservationId = generateReservationId();
        Reservation reservation = new Reservation(reservationId, user, room, checkInDate, 
                                                checkOutDate, numberOfGuests);
        reservations.add(reservation);
        
        // Note: Don't set room as unavailable here since rooms can have multiple reservations
        // for different date ranges. The availability is determined by date conflicts.
        
        System.out.println("Reservation successful! Reservation ID: " + reservationId);
        return true;
    }
    
    // Helper method to check if a room is available (not currently reserved)
    private boolean isRoomAvailable(Room room) {
        if (!room.isAvailable()) {
            return false;
        }
        
        Date currentDate = normalizeToStartOfDay(new Date());
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                // Skip cancelled reservations
                if (reservation.isCancelled()) {
                    continue;
                }
                // Normalize reservation dates
                Date resCheckIn = normalizeToStartOfDay(reservation.getCheckInDate());
                Date resCheckOut = normalizeToStartOfDay(reservation.getCheckOutDate());
                
                // Check if current date is in active reservation period
                if ((currentDate.equals(resCheckIn) || currentDate.after(resCheckIn)) &&
                        currentDate.before(resCheckOut)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Helper method to check if room is available for specific dates
    private boolean isRoomAvailableForDates(Room room, Date checkIn, Date checkOut) {
        // Normalize dates for consistent comparison
        checkIn = normalizeToStartOfDay(checkIn);
        checkOut = normalizeToStartOfDay(checkOut);
        
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                // Skip cancelled reservations
                if (reservation.isCancelled()) {
                    continue;
                }
                
                // Normalize reservation dates
                Date resCheckIn = normalizeToStartOfDay(reservation.getCheckInDate());
                Date resCheckOut = normalizeToStartOfDay(reservation.getCheckOutDate());
                
                // Check for date overlap
                // Two date ranges overlap if: start1 < end2 AND start2 < end1
                if (checkIn.before(resCheckOut) && resCheckIn.before(checkOut)) {
                    return false; // Dates overlap, room not available
                }
            }
        }
        return true; // No conflicts found
    }
    
    // Helper method to generate unique reservation ID
    private String generateReservationId() {
        return "RES-" + System.currentTimeMillis() + "-" + (reservations.size() + 1);
    }
    
    // Getter methods
    public String getHotelId() {
        return hotelId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms); // Return a copy to prevent external modification
    }
    
    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations); // Return copy to prevent external modification
    }
    
    // Setter methods
    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    // Additional utility methods
    public void displayHotelInfo() {
        System.out.println("Hotel Information:");
        System.out.println("ID: " + hotelId);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Available Rooms: " + getAvailableRooms().size());
        System.out.println("Total Reservations: " + reservations.size());
    }
    
    public void removeRoom(Room room) {
        if (rooms.remove(room)) {
            System.out.println("Room " + room.getRoomNumber() + " removed from hotel");
        } else {
            System.out.println("Room not found in hotel");
        }
    }
    
    public Room findRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
    
    public List<Reservation> getReservationsForUser(User user) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUser().equals(user)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }
}
