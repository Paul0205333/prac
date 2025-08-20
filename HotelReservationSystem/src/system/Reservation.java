package system;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {
	
    private String reservationId;
    private User user;
    private Room room;
    private java.util.Date checkInDate;
    private java.util.Date checkOutDate;
    private int numberOfGuests;
    private double totalPrice;
    private String status;

    // Reservation status constants
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_CHECKED_IN = "CHECKED_IN";
    public static final String STATUS_CHECKED_OUT = "CHECKED_OUT";
    public static final String STATUS_PENDING = "PENDING";

    // Default constructor
    Reservation() {
        this.status = STATUS_PENDING;
        this.totalPrice = 0.0;
    }

    // Parameterized constructor
    public Reservation(String reservationId, User user, Room room, Date checkInDate, 
                      Date checkOutDate, int numberOfGuests) {
        this.reservationId = reservationId;
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = STATUS_CONFIRMED;
        this.totalPrice = calculateTotalPrice();
    }

    // Alternative constructor with status
    public Reservation(String reservationId, User user, Room room, Date checkInDate, 
                      Date checkOutDate, int numberOfGuests, String status) {
        this(reservationId, user, room, checkInDate, checkOutDate, numberOfGuests);
        this.status = status != null ? status : STATUS_CONFIRMED;
    }

    // Getter methods
    public String getReservationId() {
        return reservationId;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public java.util.Date getCheckInDate() {
        return checkInDate;
    }

    public java.util.Date getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
        // Recalculate total price when room changes
        this.totalPrice = calculateTotalPrice();
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
        // Recalculate total price when dates change
        this.totalPrice = calculateTotalPrice();
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
        // Recalculate total price when dates change
        this.totalPrice = calculateTotalPrice();
    }

    public void setNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests > 0) {
            this.numberOfGuests = numberOfGuests;
        } else {
            System.out.println("Number of guests must be positive");
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Calculate total price based on room rate and number of nights
    public double calculateTotalPrice() {
        if (room == null || checkInDate == null || checkOutDate == null) {
            return 0.0;
        }

        // Calculate number of nights
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long numberOfNights = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        // Ensure at least 1 night
        if (numberOfNights <= 0) {
            numberOfNights = 1;
        }

        // Calculate base price (room rate * number of nights)
        double basePrice = room.getPrice() * numberOfNights;
        
        // You can add additional charges here if needed
        // For example: taxes, service charges, etc.
        double taxRate = 0.10; // 10% tax
        double tax = basePrice * taxRate;
        
        this.totalPrice = basePrice + tax;
        return this.totalPrice;
    }

    // Cancel the reservation
    public void cancelReservation() {
        if (STATUS_CANCELLED.equals(status)) {
            System.out.println("Reservation " + reservationId + " is already cancelled");
            return;
        }

        if (STATUS_CHECKED_IN.equals(status) || STATUS_CHECKED_OUT.equals(status)) {
            System.out.println("Cannot cancel reservation " + reservationId + 
                             " - Guest has already checked in/out");
            return;
        }

        // Update reservation status
        this.status = STATUS_CANCELLED;
        
        // Make the room available again
        if (room != null) {
            room.setAvailable(true);
        }
        
        System.out.println("Reservation " + reservationId + " has been cancelled successfully");
    }

    // Utility methods
    public long getNumberOfNights() {
        if (checkInDate == null || checkOutDate == null) {
            return 0;
        }
        
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long nights = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return nights > 0 ? nights : 1;
    }

    public boolean isActive() {
        return STATUS_CONFIRMED.equals(status) || STATUS_CHECKED_IN.equals(status);
    }

    public boolean isCancelled() {
        return STATUS_CANCELLED.equals(status);
    }

    public boolean isCompleted() {
        return STATUS_CHECKED_OUT.equals(status);
    }

    public void checkIn() {
        if (!STATUS_CONFIRMED.equals(status)) {
            System.out.println("Cannot check in - reservation status is: " + status);
            return;
        }

        Date currentDate = new Date();
        if (currentDate.before(checkInDate)) {
            System.out.println("Cannot check in before check-in date");
            return;
        }

        this.status = STATUS_CHECKED_IN;
        if (room != null) {
            room.setAvailable(false);
        }
        System.out.println("Check-in successful for reservation " + reservationId);
    }

    public void checkOut() {
        if (!STATUS_CHECKED_IN.equals(status)) {
            System.out.println("Cannot check out - guest has not checked in");
            return;
        }

        this.status = STATUS_CHECKED_OUT;
        if (room != null) {
            room.setAvailable(true);
        }
        System.out.println("Check-out successful for reservation " + reservationId);
    }

    public void displayReservationInfo() {
        System.out.println("Reservation Information:");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest: " + (user != null ? user.getName() : "N/A"));
        System.out.println("Room: " + (room != null ? room.getRoomId() + " (" + room.getRoomType() + ")" : "N/A"));
        System.out.println("Check-in Date: " + checkInDate);
        System.out.println("Check-out Date: " + checkOutDate);
        System.out.println("Number of Nights: " + getNumberOfNights());
        System.out.println("Number of Guests: " + numberOfGuests);
        System.out.println("Total Price: $" + String.format("%.2f", totalPrice));
        System.out.println("Status: " + status);
        System.out.println("------------------------");
    }

    public boolean isValidReservation() {
        return reservationId != null && !reservationId.trim().isEmpty() &&
               user != null &&
               room != null &&
               checkInDate != null &&
               checkOutDate != null &&
               checkOutDate.after(checkInDate) &&
               numberOfGuests > 0 &&
               numberOfGuests <= room.getCapacity();
    }

    public boolean isDateConflict(Date otherCheckIn, Date otherCheckOut) {
        if (checkInDate == null || checkOutDate == null || 
            otherCheckIn == null || otherCheckOut == null) {
            return false;
        }
        
        // Check if dates overlap
        return !(otherCheckOut.before(checkInDate) || otherCheckIn.after(checkOutDate));
    }

    public String getReservationSummary() {
        return String.format("Reservation %s: %s staying in %s from %s to %s (%d nights) - Status: %s",
            reservationId,
            user != null ? user.getName() : "Unknown",
            room != null ? room.getRoomId() : "Unknown",
            checkInDate,
            checkOutDate,
            getNumberOfNights(),
            status);
    }

    // Override methods for proper object handling
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation that = (Reservation) obj;
        return reservationId != null ? reservationId.equals(that.reservationId) : that.reservationId == null;
    }

    @Override
    public int hashCode() {
        return reservationId != null ? reservationId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", user=" + (user != null ? user.getName() : "null") +
                ", room=" + (room != null ? room.getRoomId() : "null") +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}
