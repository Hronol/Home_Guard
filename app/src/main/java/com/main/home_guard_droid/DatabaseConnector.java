package com.main.home_guard_droid;

import org.mariadb.jdbc.Driver;
import org.mariadb.jdbc.MariaDbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.List;

public class DatabaseConnector {

    public void makecon() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Driver driver = new org.mariadb.jdbc.Driver();
            driver = DriverManager.getDriver("http://192.168.1.71:3306");
            //driver.connect("http://192.168.1.71:3306",null);
            //Connection connection = (MariaDbConnection)DriverManager.getConnection
                    //("jdbc:mariadb://192.168.1.71:3306/Master","admin","admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
