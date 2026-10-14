package com.name.app.auth;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AuthResult auth = authenticate(username, password);

        if (auth != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("userId", auth.id);
            session.setAttribute("role", auth.role);
            recordLogin(username, auth.role);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "שם משתמש או סיסמה שגויים");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    private static class AuthResult {
        int id;
        String role;
        AuthResult(int id, String role) {
            this.id = id;
            this.role = role;
        }
    }

    private AuthResult authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        String sql = "SELECT id, password FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (dbPassword.equals(password)) {
                        int id = rs.getInt("id");
                        String role = "admin".equals(username) ? "admin" : "user";
                        return new AuthResult(id, role);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void recordLogin(String username, String role) {
        String sql = "INSERT INTO login_history (username, role) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, role);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}