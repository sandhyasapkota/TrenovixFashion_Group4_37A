/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import database.Mysql; // Ensure you have a mysql helper class for connection management
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTableDao {
    Mysql mysql = new Mysql();
    public List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = mysql.openConnection(); // Make sure you have a mysql helper class
            stmt = conn.prepareStatement("SELECT id, username, email, joined_date FROM users");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("joined_date")
                };
                users.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            // try { if (conn != null) Mysql.closeConnection(conn); } catch (Exception e) {}
        }
        return users;
    }
}
