package models;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int roomId;
    private int guestId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double total;
    private String status;

    // optional helper fields for display
    private String roomNumber;
    private String guestName;

    public Booking() {}

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    @Override
    public String toString() {
        return id + ") " + roomNumber + " | " + guestName + " | " +
               checkIn + " → " + checkOut + " | ₹" + total + " | " + status;
    }
}
