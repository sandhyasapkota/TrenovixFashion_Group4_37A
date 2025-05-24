/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author NITRO V 15
 */
public class Mysql implements Database {
    private static final Logger LOGGER = Logger.getLogger(Mysql.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/trenovixfashion";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sejan1";

    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver loaded successfully");
            
            // Try to create database and table if they don't exist
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", USERNAME, PASSWORD)) {
                LOGGER.info("Connected to MySQL server");
                
                // Create database if not exists
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS trenovixfashion");
                    LOGGER.info("Database 'trenovixfashion' created or already exists");
                    
                    // Use the database
                    stmt.executeUpdate("USE trenovixfashion");
                    
                    // Create users table if not exists
                    String createTable = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "username VARCHAR(50) NOT NULL UNIQUE, " +
                        "email VARCHAR(100) NOT NULL UNIQUE, " +
                        "password VARCHAR(100) NOT NULL, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                    stmt.executeUpdate(createTable);
                    LOGGER.info("Table 'users' created or already exists");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error initializing database: " + e.getMessage(), e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found: " + e.getMessage(), e);
        }
    }

    @Override
    public Connection openConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection == null) {
                String error = "Failed to establish database connection";
                LOGGER.severe(error);
                throw new SQLException(error);
            }
            LOGGER.info("Database connection established successfully");
            return connection;
        } catch (SQLException e) {
            String error = "Database connection failed: " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            throw new SQLException(error, e);
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                LOGGER.info("Database connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing connection: " + e.getMessage(), e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            LOGGER.info("Executing query: " + query);
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            String error = "Error executing query: " + query + " - " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            throw new SQLException(error, e);
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            LOGGER.info("Executing update: " + query);
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            String error = "Error executing update: " + query + " - " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            throw new SQLException(error, e);
        }
    }
}
    

