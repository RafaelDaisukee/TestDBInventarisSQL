package org.openjfx.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    // Prefer environment variables for credentials and URL to avoid hardcoding secrets.
    // Set DB_URL, DB_USER and DB_PASS in environment or your launch configuration.
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:sqlserver://localhost:1433;databaseName=testinventaris;encrypt=false;trustServerCertificate=true"
    );

    // Default to 'sa' with the provided password if env vars are not set.
    private static final String USER = System.getenv().getOrDefault("DB_USER", "sa");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "12345678");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
