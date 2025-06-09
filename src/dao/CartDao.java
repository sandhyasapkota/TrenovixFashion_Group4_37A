package dao;

import database.Mysql;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    Mysql mysql = new Mysql();

    // Add product to cart (increase quantity if already exists)
    

    // Remove product from cart
    public void removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Clear cart for a user
    public void clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Update quantity for a cart item
    public void updateQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}