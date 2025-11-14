package dao;

import database.DB;
import models.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // get all rooms
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT id, room_number, type, price, status FROM rooms ORDER BY room_number";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setNumber(rs.getString("room_number"));
                r.setType(rs.getString("type"));
                r.setPrice(rs.getDouble("price"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            System.err.println("RoomDAO.getAllRooms error: " + e.getMessage());
        }
        return list;
    }

    // get available rooms (status = AVAILABLE)
    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT id, room_number, type, price, status FROM rooms WHERE status='AVAILABLE' ORDER BY room_number";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setNumber(rs.getString("room_number"));
                r.setType(rs.getString("type"));
                r.setPrice(rs.getDouble("price"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException e) {
            System.err.println("RoomDAO.getAvailableRooms error: " + e.getMessage());
        }
        return list;
    }

    public double getRoomPrice(int roomId) {
        String sql = "SELECT price FROM rooms WHERE id=?";
        try (Connection con = DB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("price");
            }
        } catch (SQLException e) {
            System.err.println("RoomDAO.getRoomPrice error: " + e.getMessage());
        }
        return 0;
    }

    public Room findById(int roomId) {
        String sql = "SELECT id, room_number, type, price, status FROM rooms WHERE id=?";
        try (Connection con = DB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Room r = new Room();
                    r.setId(rs.getInt("id"));
                    r.setNumber(rs.getString("room_number"));
                    r.setType(rs.getString("type"));
                    r.setPrice(rs.getDouble("price"));
                    r.setStatus(rs.getString("status"));
                    return r;
                }
            }
        } catch (SQLException e) {
            System.err.println("RoomDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    // update room status
    public boolean updateRoomStatus(int roomId, String status) {
        String sql = "UPDATE rooms SET status=? WHERE id=?";
        try (Connection con = DB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("RoomDAO.updateRoomStatus error: " + e.getMessage());
        }
        return false;
    }
}
