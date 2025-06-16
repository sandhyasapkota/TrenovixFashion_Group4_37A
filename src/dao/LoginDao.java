/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.Mysql;
import model.User;

/**
 *
 * @author sandhya sapkota
 */
public class LoginDao {
    Mysql mysql = new Mysql();
    public boolean userlogin(User user) {
        // Code to check user credential
            
        Connection conn = mysql.openConnection();
        if (conn == null) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, "Failed to establish database connection.");
            return false;
        }
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            ResultSet result = pstmt.executeQuery();
            return result.next(); // If a user is found, return true
            // if (result.next()) {
            //     System.out.println("Login successful");
            //     JOptionPane.showMessageDialog(null, "Success: " + user.getUsername() + " logged in successfully");
            //     return true; // User exists

            // } else {
            //     System.out.println("Invalid username or password");
            //     JOptionPane.showMessageDialog(null, "Error: Invalid username or password");
            // }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
}
