package io.github.some_example_name.save;

import com.badlogic.gdx.Gdx;

import java.sql.*;

public class SQLiteDB {
    private static final String DB_NAME = "users.db";
    private static Connection connection;

    public static void initialize() {
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            connection = DriverManager.getConnection(url);

            createTables();

        } catch (SQLException e) {
            Gdx.app.error("Database", "Error initializing database", e);
        }
    }

    private static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT UNIQUE NOT NULL," +
            "password TEXT NOT NULL," +
            "answer_security_question TEXT NOT NULL," +
            "avatar BLOB NOT NULL," +
            "last_game_path TEXT," +
            "score INTEGER DEFAULT 0," +
            "survival_time INTEGER DEFAULT 0," +
            "kill_count INTEGER DEFAULT 0," +
            "auto_reload BOOLEAN DEFAULT 0)";

        executeSQL(usersTable);
    }

    private static void executeSQL(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error executing SQL: " + sql, e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error closing connection", e);
        }
    }
}
