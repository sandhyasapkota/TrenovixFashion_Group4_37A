package database;

//import Database.Database;
import java.sql.*;

/**
 *
 * @author sandhya sapkota
 */
public class Mysql implements Database {

    @Override
    public Connection openConnection() {
        try {
        String username = "root";
        String password = "sandhya64@@2005";

        String database = "trenovixfashion";

        Connection connection;
        connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/" + database,username,password
        );
        if (connection == null){
            System.out.println("Database connection Fail.");
        }  else{
            System.out.println("Database Connection Success.");
        }
        return connection;
        }catch (Exception e){
           System.out.println(e);
            return null;        
        }
        
    }

    @Override
    public void closeConnection(Connection conn) { 
        try {
            if (conn != null && !conn.isClosed()){
                conn.close();
                System.out.println("Connection close");
            }
    }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try{
            Statement stmp = conn.createStatement();
            ResultSet result = stmp.executeQuery(query);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        
   
}

    @Override
    public int executeUpdate(Connection conn, String query) {
        try{
            Statement stmp = conn.createStatement();
            int result = stmp.executeUpdate(query);
            return result;
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }    
    }
}
