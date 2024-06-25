package edu.iCET.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance;
    private Connection connection;
    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/clothify_Store","root","2003");
    }

    public Connection getConnection(){

        return connection;
    }

    public static DbConnection getInstance() throws ClassNotFoundException, SQLException {
        if (instance==null){
            return instance=new DbConnection();
        }
        return instance;
    }
}
