<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name.app.HistoryServlet.LoginRecord" %>
<!DOCTYPE html>
<html>
<head>
    <title>היסטוריית התחברויות</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: right; }
        th { background-color: #007BFF; color: white; }
        .back-link { display: inline-block; margin-top: 20px; text-decoration: none; color: #007BFF; }
    </style>
</head>
<body>
<div class="container">
    <h2>היסטוריית התחברויות למערכת</h2>

    <table>
        <tr>
            <th>שם משתמש</th>
            <th>תפקיד</th>
            <th>זמן התחברות</th>
        </tr>
        <%
            List<LoginRecord> historyList = (List<LoginRecord>) request.getAttribute("historyList");
            if (historyList != null && !historyList.isEmpty()) {
                for (LoginRecord rec : historyList) {
        %>
        <tr>
            <td><%= rec.getUsername() %></td>
            <td><%= rec.getRole() %></td>
            <td><%= rec.getLoginTime() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="3" style="text-align: center;">אין היסטוריית התחברויות להצגה.</td>
        </tr>
        <% } %>
    </table>

    <a class="back-link" href="home">← חזרה לדף הבית</a>
</div>
</body>
</html>