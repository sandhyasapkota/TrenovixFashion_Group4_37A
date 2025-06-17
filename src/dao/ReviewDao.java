package dao;

import database.Mysql;
import model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    Mysql mysql = new Mysql();

    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, product_id, review_text) VALUES (?, ?, ?)";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getProductId());
            stmt.setString(3, review.getReviewText());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Fetch reviews with product and user info for table display
    public List<Object[]> getReviewTableData() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT r.product_id, p.name AS product_name, u.username AS customer_name, r.review_date, r.review_text " +
                     "FROM reviews r " +
                     "JOIN products p ON r.product_id = p.id " +
                     "JOIN users u ON r.user_id = u.id " + // <-- changed u.user_id to u.id
                     "ORDER BY r.review_date DESC";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("customer_name"),
                    rs.getTimestamp("review_date"),
                    rs.getString("review_text")
                };
                data.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}