package com.name.app;

import com.name.app.db.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/history.jsp")
public class HistoryServlet extends HttpServlet {

    public static class LoginRecord {
        private String username;
        private String role;
        private String loginTime;

        public LoginRecord(String username, String role, String loginTime) {
            this.username = username;
            this.role = role;
            this.loginTime = loginTime;
        }

        public String getUsername() { return username; }
        public String getRole() { return role; }
        public String getLoginTime() { return loginTime; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LoginRecord> historyList = new ArrayList<>();
        String sql = "SELECT username, role, login_time FROM login_history ORDER BY login_time DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                historyList.add(new LoginRecord(
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("login_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("/WEB-INF/views/history.jsp.jsp").forward(request, response);
    }
}