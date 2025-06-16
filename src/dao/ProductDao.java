package dao;

import database.Mysql;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Mysql mysql = new Mysql();

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, size, price, category, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSize());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getImageUrl());
            stmt.setInt(6, product.getQuantity());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = mysql.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"), 
                    rs.getString("name"),
                    rs.getString("size"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getString("image_url"),
                    rs.getInt("quantity")
                );
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
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
        String sql = "UPDATE products SET name=?, size=?, price=?, category=?, image_url=?, quantity=? WHERE id=?";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSize());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setString(5, product.getImageUrl());
            stmt.setInt(6, product.getQuantity());
            stmt.setInt(7, product.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}