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

@WebServlet({"/admin-login", "/admin-dashboard"})
public class AdminServlet extends HttpServlet {

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

            Map<String, List<String>> userBooksMap = new HashMap<>();
            String sql = "SELECT u.username, b.title FROM users u " +
                    "LEFT JOIN user_books ub ON u.id = ub.user_id " +
                    "LEFT JOIN books b ON ub.book_id = b.id";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = rs.getString("username");
                    String bookTitle = rs.getString("title");

                    userBooksMap.putIfAbsent(username, new ArrayList<>());
                    if (bookTitle != null) {
                        userBooksMap.get(username).add(bookTitle);
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
        String password = request.getParameter("adminPassword");

        if ("אין על קליין".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("isAdmin", true);
            response.sendRedirect(request.getContextPath() + "/admin-dashboard");
        } else {
            request.setAttribute("error", "סיסמה שגויה! נסה שוב.");
            request.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(request, response);
        }
    }
}