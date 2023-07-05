package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private final String url;
    private final Properties properties;

    public DatabaseConnectionManager() {
        this.url = "jdbc:postgresql:meals_db";
        this.properties = new Properties();
        this.properties.setProperty("user", "postgres");
        this.properties.setProperty("password", "1111");
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(this.url, properties);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
