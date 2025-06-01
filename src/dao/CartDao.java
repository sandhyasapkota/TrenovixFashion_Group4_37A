package dao;

import database.Mysql;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    Mysql mysql = new Mysql();

    // Add product to cart (increase quantity if already exists)
    public boolean addToCart(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Get all cart items for a user
    public List<Product> getCartItems(int userId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.quantity as cart_quantity FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("size"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getString("image_url"),
                    rs.getInt("cart_quantity"), // quantity from cart, not product stock
                    rs.getString("description")
                );
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

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