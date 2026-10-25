package com.name.app;

import com.name.app.db.DatabaseManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing database...");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS messages (" +
                            "  id   INT AUTO_INCREMENT PRIMARY KEY," +
                            "  text VARCHAR(255) NOT NULL" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS login_history (" +
                            "  id          INT AUTO_INCREMENT PRIMARY KEY," +
                            "  username  VARCHAR(50) NOT NULL," +
                            "  role      VARCHAR(20) NOT NULL," +
                            "  login_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "  id INT AUTO_INCREMENT PRIMARY KEY," +
                            "  username VARCHAR(50) UNIQUE NOT NULL," +
                            "  password VARCHAR(100) NOT NULL" +
                            ")"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS books (" +
                            "  id INT AUTO_INCREMENT PRIMARY KEY," +
                            "  title VARCHAR(150) NOT NULL," +
                            "  author VARCHAR(100) NOT NULL" +
                            ")"
            );

            stmt.execute(
                    "INSERT INTO books (title, author) " +
                            "SELECT 'הארי פוטר', 'ג.ק. רולינג' WHERE NOT EXISTS (SELECT 1 FROM books);"
            );
            stmt.execute(
                    "INSERT INTO books (title, author) " +
                            "SELECT 'שר הטבעות', 'ג.ר.ר. טולקין' WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'שר הטבעות');"
            );
            stmt.execute(
                    "INSERT INTO books (title, author) " +
                            "SELECT 'הנסיך הקטן', 'אנטואן דה סנט-אכזופרי' WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'הנסיך הקטן');"
            );

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_books (" +
                            "  id INT AUTO_INCREMENT PRIMARY KEY," +
                            "  user_id INT NOT NULL," +
                            "  book_id INT NOT NULL," +
                            "  borrowed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "  FOREIGN KEY (user_id) REFERENCES users(id)," +
                            "  FOREIGN KEY (book_id) REFERENCES books(id)" +
                            ")"
            );

            System.out.println("Database ready.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App shutting down.");
    }
}