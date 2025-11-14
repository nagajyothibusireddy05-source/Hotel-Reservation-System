package main;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Self-contained Main that compiles even if your real DAOs are not present.
 * Replace the stub DAO classes below with your actual DAO classes when ready.
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Use real DAOs in your project; these stubs make this file compile right now.
        RoomDAO roomDAO = new RoomDAO();
        GuestDAO guestDAO = new GuestDAO();
        BookingDAO bookingDAO = new BookingDAO();

        while (true) {
            System.out.println("\n--- HOTEL RESERVATION SYSTEM ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number.");
                continue;
            }

            switch (ch) {
                case 1 -> {
                    List<Room> rooms = roomDAO.getAvailableRooms();
                    System.out.println("\nAvailable Rooms:");
                    if (rooms.isEmpty()) {
                        System.out.println("No rooms available.");
                    } else {
                        rooms.forEach(System.out::println);
                    }
                }

                case 2 -> {
                    System.out.print("Guest Name: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Guest Phone: ");
                    String phone = sc.nextLine().trim();

                    LocalDate checkIn;
                    LocalDate checkOut;
                    try {
                        System.out.print("Check-in date (YYYY-MM-DD): ");
                        checkIn = LocalDate.parse(sc.nextLine().trim());

                        System.out.print("Check-out date (YYYY-MM-DD): ");
                        checkOut = LocalDate.parse(sc.nextLine().trim());
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Use YYYY-MM-DD.");
                        break;
                    }

                    // The stubs below return dummy values. Replace calls with your real DAO methods.
                    int guestId = guestDAO.createGuest(name, phone);
                    Room selectedRoom = roomDAO.findAvailableRoom(checkIn, checkOut);

                    if (selectedRoom == null) {
                        System.out.println("No rooms available for the selected dates.");
                    } else {
                        boolean ok = bookingDAO.createBooking(guestId, selectedRoom.getId(), checkIn, checkOut);
                        System.out.println(ok ? "Booking successful!" : "Booking failed. Please try again.");
                    }
                }

                case 3 -> {
                    System.out.print("Enter booking ID to cancel: ");
                    String idStr = sc.nextLine().trim();
                    try {
                        int bookingId = Integer.parseInt(idStr);
                        boolean cancelled = bookingDAO.cancelBooking(bookingId);
                        System.out.println(cancelled ? "Booking cancelled." : "Cancellation failed or booking not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid booking ID.");
                    }
                }

                case 4 -> {
                    System.out.println("\nAll bookings:");
                    List<String> all = bookingDAO.getAllBookings();
                    if (all.isEmpty()) System.out.println("No bookings found.");
                    else all.forEach(System.out::println);
                }

                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }

                default -> System.out.println("Invalid option. Please choose 1-5.");
            }
        }
    }
}

/* -------------------------
   Minimal stub implementations
   -------------------------
   These classes are package-private and only exist so this file compiles.
   Replace/remove them when integrating with your real DAO classes.
*/

class Room {
    private final int id;
    private final String type;
    private final double price;

    public Room(int id, String type, double price) {
        this.id = id; this.type = type; this.price = price;
    }

    public int getId() { return id; }
    @Override
    public String toString() {
         return "Room{id=" + id + ", type=" + type + ", price=" + price + "}"; }
}

class RoomDAO {
    // Return some dummy available rooms
    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        list.add(new Room(101, "Single", 50.0));
        list.add(new Room(102, "Double", 80.0));
        return list;
    }

    // Simple stub: return the first room if any
    public Room findAvailableRoom(LocalDate in, LocalDate out) {
        List<Room> rooms = getAvailableRooms();
        return rooms.isEmpty() ? null : rooms.get(0);
    }
}

class GuestDAO {
    // Stub: pretend to create guest and return a generated ID
    private int nextId = 1;
    public int createGuest(String name, String phone) {
        System.out.println("Stub: created guest '" + name + "' with phone " + phone);
        return nextId++;
    }
}

class BookingDAO {
    // Stub store
    private final List<String> bookings = new ArrayList<>();
    private int nextBookingId = 1;

    public boolean createBooking(int guestId, int roomId, LocalDate in, LocalDate out) {
        String rec = "Booking#" + (nextBookingId++) + " guest=" + guestId + " room=" + roomId + " from=" + in + " to=" + out;
        bookings.add(rec);
        System.out.println("Stub: " + rec);
        return true;
    }

    public boolean cancelBooking(int bookingId) {
        // naive cancel: remove by index-like id
        // String keyPrefix = "Booking#" + bookingId + " ";
        for (String b : new ArrayList<>(bookings)) {
            if (b.startsWith("Booking#" + bookingId)) {
                bookings.remove(b);
                return true;
            }
        }
        return false;
    }

    public List<String> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}
