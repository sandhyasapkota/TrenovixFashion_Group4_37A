package dao;

import database.Mysql;
import model.UserProfile;
import java.sql.*;

public class UserProfileDao {
    Mysql mysql = new Mysql();

    public UserProfile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM user_profiles WHERE user_id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserProfile profile = new UserProfile();
                profile.setProfileId(rs.getInt("profile_id"));
                profile.setUserId(rs.getInt("user_id"));
                profile.setFirstName(rs.getString("first_name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setAge(rs.getInt("age"));
                profile.setGender(rs.getString("gender"));
                profile.setEmail(rs.getString("email"));
                profile.setCountry(rs.getString("country"));
                profile.setContact(rs.getString("contact"));
                profile.setAddress(rs.getString("address"));
                return profile;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean saveOrUpdateProfile(UserProfile profile) {
        String checkSql = "SELECT profile_id FROM user_profiles WHERE user_id = ?";
        String insertSql = "INSERT INTO user_profiles (user_id, first_name, last_name, age, gender, country, contact, address, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE user_profiles SET first_name=?, last_name=?, age=?, gender=?, country=?, contact=?, address=?, email=? WHERE user_id=?";
        
        try (Connection conn = mysql.openConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            
            checkStmt.setInt(1, profile.getUserId());
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Update existing profile
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, profile.getFirstName());
                    stmt.setString(2, profile.getLastName());
                    stmt.setInt(3, profile.getAge());
                    stmt.setString(4, profile.getGender());
                    stmt.setString(5, profile.getCountry());
                    stmt.setString(6, profile.getContact());
                    stmt.setString(7, profile.getAddress());
                    stmt.setString(8, profile.getEmail());
                    stmt.setInt(9, profile.getUserId());
                    
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                // Insert new profile
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setInt(1, profile.getUserId());
                    stmt.setString(2, profile.getFirstName());
                    stmt.setString(3, profile.getLastName());
                    stmt.setInt(4, profile.getAge());
                    stmt.setString(5, profile.getGender());
                    stmt.setString(6, profile.getCountry());
                    stmt.setString(7, profile.getContact());
                    stmt.setString(8, profile.getAddress());
                    stmt.setString(9, profile.getEmail());
                    
                    int rowsAffected = stmt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}