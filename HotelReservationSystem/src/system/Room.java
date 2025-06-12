package system;

public class Room {
	
    private String roomId;
    private String roomType;
    private double price;
    private boolean isAvailable;
    private int capacity;
    private String amenities;

    // Default constructor
    Room() {
        this.isAvailable = true; // Rooms are available by default
        this.amenities = ""; // Default empty amenities
    }

    // Parameterized constructor
    public Room(String roomId, String roomType, double price, int capacity, String amenities) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.capacity = capacity;
        this.amenities = amenities != null ? amenities : "";
        this.isAvailable = true; // New rooms are available by default
    }

    // Alternative constructor without amenities
    public Room(String roomId, String roomType, double price, int capacity) {
        this(roomId, roomType, price, capacity, "");
    }

    // Getter methods
    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }
    
    // Alternative getter method with standard naming convention
    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAmenities() {
        return amenities;
    }
    
    // Missing method that Hotel class expects
    public String getRoomNumber() {
        return roomId; // Assuming roomId serves as room number
    }

    // Setter methods
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative");
        }
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    // Alternative setter with standard naming
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            System.out.println("Capacity must be positive");
        }
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities != null ? amenities : "";
    }

    // Utility methods
    public void displayRoomInfo() {
        System.out.println("Room Information:");
        System.out.println("Room ID: " + roomId);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Capacity: " + capacity + " guests");
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Amenities: " + (amenities.isEmpty() ? "None" : amenities));
        System.out.println("------------------------");
    }

    public void addAmenity(String amenity) {
        if (amenity != null && !amenity.trim().isEmpty()) {
            if (amenities.isEmpty()) {
                amenities = amenity.trim();
            } else {
                amenities += ", " + amenity.trim();
            }
        }
    }

    public void removeAmenity(String amenity) {
        if (amenity != null && !amenities.isEmpty()) {
            amenities = amenities.replace(amenity, "").replaceAll(", ,", ",").trim();
            if (amenities.startsWith(",")) {
                amenities = amenities.substring(1).trim();
            }
            if (amenities.endsWith(",")) {
                amenities = amenities.substring(0, amenities.length() - 1).trim();
            }
        }
    }

    public boolean hasAmenity(String amenity) {
        return amenity != null && amenities.toLowerCase().contains(amenity.toLowerCase());
    }

    public void bookRoom() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Room " + roomId + " has been booked");
        } else {
            System.out.println("Room " + roomId + " is already booked");
        }
    }

    public void checkOut() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("Room " + roomId + " is now available for booking");
        } else {
            System.out.println("Room " + roomId + " is already available");
        }
    }

    public double calculateTotalPrice(int numberOfNights) {
        if (numberOfNights > 0) {
            return price * numberOfNights;
        }
        return 0.0;
    }

    public boolean canAccommodate(int numberOfGuests) {
        return numberOfGuests <= capacity && numberOfGuests > 0;
    }

    // Override equals method for proper comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomId != null ? roomId.equals(room.roomId) : room.roomId == null;
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return roomId != null ? roomId.hashCode() : 0;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", capacity=" + capacity +
                ", amenities='" + amenities + '\'' +
                '}';
    }

    // Validation method
    public boolean isValidRoom() {
        return roomId != null && !roomId.trim().isEmpty() &&
               roomType != null && !roomType.trim().isEmpty() &&
               price >= 0 && capacity > 0;
    }

    // Room status methods
    public String getAvailabilityStatus() {
        return isAvailable ? "Available" : "Occupied";
    }

    public String getRoomDescription() {
        StringBuilder description = new StringBuilder();
        description.append(roomType).append(" room (ID: ").append(roomId).append(") ");
        description.append("- $").append(String.format("%.2f", price)).append(" per night, ");
        description.append("accommodates ").append(capacity).append(" guests");
        
        if (!amenities.isEmpty()) {
            description.append(", amenities: ").append(amenities);
        }
        
        return description.toString();
    }
}
