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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/admin-login", "/admin-dashboard", "/admin-delete-book"})
public class AdminServlet extends HttpServlet {

    public static class BookInfo {
        private int id;
        private String title;

        public BookInfo(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/admin-login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(request, response);
        } else if ("/admin-dashboard".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session == null || !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
                response.sendRedirect(request.getContextPath() + "/admin-login");
                return;
            }

            Map<String, List<BookInfo>> userBooksMap = new HashMap<>();
            String sql = "SELECT u.username, b.id as book_id, b.title FROM users u " +
                    "LEFT JOIN user_books ub ON u.id = ub.user_id " +
                    "LEFT JOIN books b ON ub.book_id = b.id";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = rs.getString("username");
                    int bookId = rs.getInt("book_id");
                    String bookTitle = rs.getString("title");

                    userBooksMap.putIfAbsent(username, new ArrayList<>());
                    if (bookTitle != null) {
                        userBooksMap.get(username).add(new BookInfo(bookId, bookTitle));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("userBooksMap", userBooksMap);
            request.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/admin-login".equals(path)) {
            String password = request.getParameter("adminPassword");
            if ("אין על קליין".equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("isAdmin", true);
                response.sendRedirect(request.getContextPath() + "/admin-dashboard");
            } else {
                request.setAttribute("error", "סיסמה שגויה! נסה שוב.");
                request.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(request, response);
            }
        } else if ("/admin-delete-book".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session == null || !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
                response.sendRedirect(request.getContextPath() + "/admin-login");
                return;
            }

            String bookIdStr = request.getParameter("bookId");
            if (bookIdStr != null) {
                try {
                    int bookId = Integer.parseInt(bookIdStr);
                    try (Connection conn = DatabaseManager.getConnection()) {
                        // מחיקת הקשר מטבלת הקישור
                        String deleteLinks = "DELETE FROM user_books WHERE book_id = ?";
                        try (PreparedStatement ps1 = conn.prepareStatement(deleteLinks)) {
                            ps1.setInt(1, bookId);
                            ps1.executeUpdate();
                        }

                        // מחיקת הספר מטבלת הספרים הכללית
                        String deleteBook = "DELETE FROM books WHERE id = ?";
                        try (PreparedStatement ps2 = conn.prepareStatement(deleteBook)) {
                            ps2.setInt(1, bookId);
                            ps2.executeUpdate();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin-dashboard");
        }
    }
}