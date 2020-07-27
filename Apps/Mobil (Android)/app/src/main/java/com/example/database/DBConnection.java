package com.example.database;

import com.example.Statics;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con = null;

    public static Connection connection() {
        String connectionUrl = Statics.DATABASE_CREDENTIALS;

        if (con == null) {
            try {

                Class.forName(Statics.DATABASE_DRIVER);
                con = DriverManager.getConnection(connectionUrl);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return con;
    }

}
