package dao;

import database.Mysql;
import model.Order;
import model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    Mysql mysql = new Mysql();

    public boolean placeOrder(Order order) {
        String orderSql = "INSERT INTO orders (user_id, first_name, last_name, phone, email, province, city, postal_code, street_address, tole) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = mysql.openConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setString(2, order.getFirstName());
                orderStmt.setString(3, order.getLastName());
                orderStmt.setString(4, order.getPhone());
                orderStmt.setString(5, order.getEmail());
                orderStmt.setString(6, order.getProvince());
                orderStmt.setString(7, order.getCity());
                orderStmt.setString(8, order.getPostalCode());
                orderStmt.setString(9, order.getStreetAddress());
                orderStmt.setString(10, order.getTole());
                orderStmt.executeUpdate();
                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    order.setOrderId(orderId); // <-- Set the generated order ID here!
                    for (OrderItem item : order.getItems()) {
                        try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, item.getProductId());
                            itemStmt.setInt(3, item.getQuantity());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.executeUpdate();
                        }
                    }
                }
                conn.commit();
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public List<Object[]> getOrderTableData() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT oi.product_id, p.name AS product_name, u.username AS customer_name, o.order_date, oi.price " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.order_id " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "ORDER BY o.order_date DESC";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("customer_name"),
                    rs.getTimestamp("order_date"),
                    rs.getDouble("price")
                };
                data.add(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public int getTodaysOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public double getTotalSales() {
        String sql = "SELECT SUM(oi.price * oi.quantity) FROM order_items oi";
        try (Connection conn = mysql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }
}