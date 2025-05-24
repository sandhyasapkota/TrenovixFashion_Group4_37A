/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NITRO V 15
 */
    public interface Database {
    Connection openConnection() throws SQLException;
    void closeConnection(Connection conn);
    ResultSet runQuery(Connection conn, String query) throws SQLException;
    int executeUpdate(Connection conn, String query) throws SQLException;
};

