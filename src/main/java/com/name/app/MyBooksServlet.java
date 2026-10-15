package com.name.app;

import com.name.app.db.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/my-books")
public class MyBooksServlet extends HttpServlet {

    public static class Book {
        private int id;
        private String title;
        private String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        List<Book> userBooks = new ArrayList<>();

        String sql = "SELECT b.id, b.title, b.author FROM books b " +
                "JOIN user_books ub ON b.id = ub.book_id " +
                "WHERE ub.user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userBooks.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("books", userBooks);
        request.getRequestDispatcher("/WEB-INF/views/my-books.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        if (title != null && !title.trim().isEmpty() && author != null && !author.trim().isEmpty()) {
            try (Connection conn = DatabaseManager.getConnection()) {
                String insertBookSql = "INSERT INTO books (title, author) VALUES (?, ?)";
                int bookId = -1;

                try (PreparedStatement psBook = conn.prepareStatement(insertBookSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    psBook.setString(1, title);
                    psBook.setString(2, author);
                    psBook.executeUpdate();

                    try (ResultSet rsKeys = psBook.getGeneratedKeys()) {
                        if (rsKeys.next()) {
                            bookId = rsKeys.getInt(1);
                        }
                    }
                }

                if (bookId != -1) {
                    String linkSql = "INSERT INTO user_books (user_id, book_id) VALUES (?, ?)";
                    try (PreparedStatement psLink = conn.prepareStatement(linkSql)) {
                        psLink.setInt(1, userId);
                        psLink.setInt(2, bookId);
                        psLink.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/my-books");
    }
}