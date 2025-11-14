package dao;

import database.DB;

import java.sql.*;

public class GuestDAO {

    // insert guest and return generated id
    public int saveGuest(String name, String phone) {
        String sql = "INSERT INTO guests (name, phone) VALUES (?, ?)";
        try (Connection con = DB.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("GuestDAO.saveGuest error: " + e.getMessage());
        }
        return -1;
    }

    // optional: try to find existing guest by phone (returns id or -1)
    public int findGuestByPhone(String phone) {
        String sql = "SELECT id FROM guests WHERE phone = ?";
        try (Connection con = DB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("GuestDAO.findGuestByPhone error: " + e.getMessage());
        }
        return -1;
    }
}
