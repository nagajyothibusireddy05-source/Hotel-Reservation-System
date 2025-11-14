package dao;

import database.DB;
import models.Booking;

import java.sql.*;
// import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // create booking and return generated id (or -1)
    public int createBooking(Booking b) {
        String sql = "INSERT INTO bookings (room_id, guest_id, check_in, check_out, total, status) VALUES (?,?,?,?,?, 'CONFIRMED')";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, b.getRoomId());
            ps.setInt(2, b.getGuestId());
            ps.setDate(3, Date.valueOf(b.getCheckIn()));
            ps.setDate(4, Date.valueOf(b.getCheckOut()));
            ps.setDouble(5, b.getTotal());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int bookingId = rs.getInt(1);
                    // mark room occupied
                    new RoomDAO().updateRoomStatus(b.getRoomId(), "OCCUPIED");
                    return bookingId;
                }
            }

        } catch (SQLException e) {
            System.err.println("BookingDAO.createBooking error: " + e.getMessage());
        }
        return -1;
    }

    // cancel booking and free room
    public boolean cancelBooking(int bookingId) {
        String getRoomSql = "SELECT room_id FROM bookings WHERE id = ?";
        String updateBookingSql = "UPDATE bookings SET status='CANCELLED' WHERE id = ?";
        try (Connection con = DB.connect();
             PreparedStatement ps1 = con.prepareStatement(getRoomSql)) {

            ps1.setInt(1, bookingId);
            try (ResultSet rs = ps1.executeQuery()) {
                if (rs.next()) {
                    int roomId = rs.getInt("room_id");

                    // update booking status
                    try (PreparedStatement ps2 = con.prepareStatement(updateBookingSql)) {
                        ps2.setInt(1, bookingId);
                        ps2.executeUpdate();
                    }

                    // free the room
                    new RoomDAO().updateRoomStatus(roomId, "AVAILABLE");
                    return true;
                } else {
                    System.err.println("Booking id not found: " + bookingId);
                }
            }

        } catch (SQLException e) {
            System.err.println("BookingDAO.cancelBooking error: " + e.getMessage());
        }
        return false;
    }

    // list bookings (most recent first)
    public List<Booking> getBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, r.room_number, g.name as guest_name FROM bookings b " +
                     "JOIN rooms r ON b.room_id = r.id JOIN guests g ON b.guest_id = g.id " +
                     "ORDER BY b.created_at DESC";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setRoomId(rs.getInt("room_id"));
                b.setGuestId(rs.getInt("guest_id"));
                Date ci = rs.getDate("check_in");
                Date co = rs.getDate("check_out");
                b.setCheckIn(ci != null ? ci.toLocalDate() : null);
                b.setCheckOut(co != null ? co.toLocalDate() : null);
                b.setTotal(rs.getDouble("total"));
                b.setStatus(rs.getString("status"));
                b.setRoomNumber(rs.getString("room_number"));
                b.setGuestName(rs.getString("guest_name"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("BookingDAO.getBookings error: " + e.getMessage());
        }
        return list;
    }
}
