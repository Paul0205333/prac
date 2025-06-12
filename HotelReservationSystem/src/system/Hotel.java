package system;

import java.util.*;

public class Hotel {
    private String hotelId;
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Reservation> reservations;

    
    
    // Default constructor
    Hotel() {
        this.rooms = new ArrayList<>();
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

    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
            System.out.println("Room " + room.getRoomNumber() + " added to hotel " + name);
        } else {
            System.out.println("Cannot add null room");
        }
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        
        for (Room room : rooms) {
            if (isRoomAvailable(room)) {
                availableRooms.add(room);
            }
        }
        
        return availableRooms;
    }

    public void makeReservation(User user, Room room, java.util.Date checkInDate, 
                               java.util.Date checkOutDate, int numberOfGuests) {
        
        // Validate inputs
        if (user == null) {
            System.out.println("Reservation failed: User cannot be null");
            return;
        }
        
        if (room == null) {
            System.out.println("Reservation failed: Room cannot be null");
            return;
        }
        
        if (checkInDate == null || checkOutDate == null) {
            System.out.println("Reservation failed: Check-in and check-out dates cannot be null");
            return;
        }
        
        if (checkInDate.after(checkOutDate)) {
            System.out.println("Reservation failed: Check-in date must be before check-out date");
            return;
        }
        
        if (numberOfGuests <= 0) {
            System.out.println("Reservation failed: Number of guests must be positive");
            return;
        }
        
        if (numberOfGuests > room.getCapacity()) {
            System.out.println("Reservation failed: Number of guests exceeds room capacity");
            return;
        }
        
        // Check if room exists in this hotel
        if (!rooms.contains(room)) {
            System.out.println("Reservation failed: Room not found in this hotel");
            return;
        }
        
        // Check if room is available for the given dates
        if (!isRoomAvailableForDates(room, checkInDate, checkOutDate)) {
            System.out.println("Reservation failed: Room is not available for the selected dates");
            return;
        }
        
        // Create and add reservation
        String reservationId = generateReservationId();
        Reservation reservation = new Reservation(reservationId, user, room, checkInDate, 
                                                checkOutDate, numberOfGuests);
        reservations.add(reservation);
        
        // Mark room as reserved (you might want to update room status)
        room.setAvailable(false);
        
        System.out.println("Reservation successful! Reservation ID: " + reservationId);
    }
    
    // Helper method to check if a room is available (not currently reserved)
    private boolean isRoomAvailable(Room room) {
        if (!room.isAvailable()) {
            return false;
        }
        
        // Check if room has any active reservations
        Date currentDate = new Date();
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                // Check if current date falls within any reservation period
                if (currentDate.after(reservation.getCheckInDate()) && 
                    currentDate.before(reservation.getCheckOutDate())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Helper method to check if room is available for specific dates
    private boolean isRoomAvailableForDates(Room room, Date checkIn, Date checkOut) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                // Check for date overlap
                if (!(checkOut.before(reservation.getCheckInDate()) || 
                      checkIn.after(reservation.getCheckOutDate()))) {
                    return false; // Dates overlap with existing reservation
                }
            }
        }
        return true;
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
        return new ArrayList<>(rooms); // Return copy to prevent external modification
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
