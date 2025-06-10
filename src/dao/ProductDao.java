package dao;

import database.Mysql;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Mysql mysql = new Mysql();

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, size, price, category, image_url, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSize());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getImageUrl());
            stmt.setInt(6, product.getQuantity());
            stmt.setString(7, product.getDescription()); // <-- add this line
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

   

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, size=?, price=?, category=?, image_url=?, quantity=?, description=? WHERE id=?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSize());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getImageUrl());
            stmt.setInt(6, product.getQuantity());
            stmt.setString(7, product.getDescription());
            stmt.setInt(8, product.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getLowStockProducts(int threshold) {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT id, name, quantity, category FROM products WHERE quantity <= ?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("category")
                };
                data.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}