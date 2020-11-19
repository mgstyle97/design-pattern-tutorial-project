package io.wisoft.project.dao.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlAccess {
    private static Connection conn = null;

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/design_pattern";
            String username = "migni";
            String password = "!alsrlrla97";

            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return conn;
    }
}
