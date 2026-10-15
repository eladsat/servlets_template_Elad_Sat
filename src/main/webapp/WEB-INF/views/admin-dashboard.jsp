<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>לוח בקרה - ניהול ספרייה</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 700px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: right; vertical-align: top; }
        th { background-color: #343a40; color: white; }
        ul { margin: 0; padding-right: 20px; }
        .logout { display: inline-block; margin-top: 20px; color: #dc3545; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>לוח בקרה למנהל מערכת 📊</h2>
    <p>סקירה של כל המשתמשים הרשומים והספרים המשויכים לכל אחד מהם:</p>

    <table>
        <tr>
            <th>שם משתמש</th>
            <th>הספרים ברשימה האישית</th>
        </tr>
        <%
            Map<String, List<String>> userBooksMap = (Map<String, List<String>>) request.getAttribute("userBooksMap");
            if (userBooksMap != null && !userBooksMap.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : userBooksMap.entrySet()) {
        %>
        <tr>
            <td><strong><%= entry.getKey() %></strong></td>
            <td>
                <%
                    List<String> books = entry.getValue();
                    if (books != null && !books.isEmpty()) {
                %>
                    <ul>
                    <% for (String book : books) { %>
                        <li><%= book %></li>
                    <% } %>
                    </ul>
                <% } else { %>
                    <span style="color: #888;">אין ספרים ברשימה</span>
                <% } %>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="2" style="text-align: center;">אין משתמשים רשומים במערכת.</td>
        </tr>
        <% } %>
    </table>

    <a class="logout" href="my-books">← חזרה לעמוד הראשי</a>
</div>
</body>
</html>