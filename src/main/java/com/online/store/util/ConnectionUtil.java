package com.online.store.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find mySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "1111");
        //String url = "jdbc:mysql://localhost:3306/online_store";
        String url = "jdbc:mysql://localhost:3306/online_store?serverTimezone=UTC";
        try {
            Connection connection = DriverManager.getConnection(url, dbProperties);
            System.out.println("Connection to DB established");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Can't established the connection to DB" , e);
        }
    }
}
