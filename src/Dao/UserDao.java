/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import database.Mysql;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NITRO V 15
 */
public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    Mysql mysql = new Mysql();
    
    
    // Data access methods for User entities
    public void signUp(User user) throws SQLException {
        Connection conn = mysql.openConnection();
        if (conn == null) {
            throw new SQLException("Could not connect to database");
        }
        
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            int result = pstmt.executeUpdate();
            if (result != 1) {
                throw new SQLException("Failed to create user account");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "Error creating user", ex);
            throw ex;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    public boolean checkUser(User user) throws SQLException {
        Connection conn = mysql.openConnection();
        if (conn == null) {
            throw new SQLException("Could not connect to database");
        }
        
        String sql = "SELECT * FROM users WHERE email = ? OR username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUsername());
            ResultSet result = pstmt.executeQuery();
            return result.next(); // If a user is found, return true
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, "Error checking user existence", ex);
            throw ex;
        } finally {
            mysql.closeConnection(conn);
        }
    }

    public boolean verifyLogin(String username, String password) throws SQLException {
        Connection conn = mysql.openConnection();
        if (conn == null) {
            LOGGER.severe("Database connection failed");
            throw new SQLException("Could not connect to database");
        }
        
        LOGGER.info("Attempting login for username: " + username);
        String sql = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, username); // Allow login with either username or email
            pstmt.setString(3, password);
            
            // Log the actual SQL query with parameters
            LOGGER.info("Executing query: " + sql);
            LOGGER.info("Parameters: username/email=" + username + ", password=" + password);
            
            ResultSet result = pstmt.executeQuery();
            boolean hasUser = result.next();
            
            if (!hasUser) {
                // If no user found, try to find why
                sql = "SELECT * FROM users WHERE username = ? OR email = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(sql)) {
                    checkStmt.setString(1, username);
                    checkStmt.setString(2, username);
                    ResultSet checkResult = checkStmt.executeQuery();
                    if (checkResult.next()) {
                        LOGGER.info("User found but password doesn't match");
                    } else {
                        LOGGER.info("No user found with username/email: " + username);
                    }
                }
            }
            
            LOGGER.info("Login result: " + (hasUser ? "User found" : "User not found"));
            return hasUser;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error verifying login", ex);
            throw ex;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
