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
import database.*;

/**
 *
 * @author sandhya sapkota
 */
public class Forgot {
    

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


    Mysql mysql = new Mysql();
    
    public boolean validateSecurityAnswer(String username, String email, String security_ans) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT security_ans FROM users WHERE username = ? AND email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                String storedAnswer = result.getString("security_ans");
                return storedAnswer.equals(security_ans); // Compare answers
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
    public boolean updatePasswordWithSecurityAnswer(String username, String email, String security_ans, String newPassword) {
        Connection conn = mysql.openConnection();
        String validateSql = "SELECT security_ans FROM users WHERE username = ? AND email = ?";
        try (PreparedStatement validateStmt = conn.prepareStatement(validateSql)) {
            validateStmt.setString(1, username);
            validateStmt.setString(2, email);
            ResultSet result = validateStmt.executeQuery();
            if (result.next()) {
                String storedAnswer = result.getString("security_ans");
                if (storedAnswer.equals(security_ans)) {
                    // Security answer matches, proceed to update password
                    String updateSql = "UPDATE users SET password = ? WHERE username = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, newPassword);
                        updateStmt.setString(2, username);
                        int rowsUpdated = updateStmt.executeUpdate();
                        return rowsUpdated > 0; // Return true if password was updated
                    }
                } else {
                    return false; // Security answer does not match
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Return false if an exception occurs
        } finally {
            mysql.closeConnection(conn);
        }
        return false; // Return false if user not found or other issues
    }
}

