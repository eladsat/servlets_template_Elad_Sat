<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name.app.AdminServlet.BookInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>לוח בקרה - ניהול ספרייה</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 750px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: right; vertical-align: top; }
        th { background-color: #343a40; color: white; }
        ul { margin: 0; padding-right: 20px; }
        li { margin-bottom: 8px; display: flex; justify-content: space-between; align-items: center; }
        .delete-btn { background-color: #dc3545; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; font-size: 12px; }
        .delete-btn:hover { background-color: #c82333; }
        .logout { display: inline-block; margin-top: 20px; color: #dc3545; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>לוח בקרה למנהל מערכת 📊</h2>
    <p>סקירה של כל המשתמשים הרשומים והספרים המשויכים אליהם:</p>

    <table>
        <tr>
            <th>שם משתמש</th>
            <th>הספרים ברשימה האישית</th>
        </tr>
        <%
            Map<String, List<BookInfo>> userBooksMap = (Map<String, List<BookInfo>>) request.getAttribute("userBooksMap");
            if (userBooksMap != null && !userBooksMap.isEmpty()) {
                for (Map.Entry<String, List<BookInfo>> entry : userBooksMap.entrySet()) {
        %>
        <tr>
            <td><strong><%= entry.getKey() %></strong></td>
            <td>
                <%
                    List<BookInfo> books = entry.getValue();
                    if (books != null && !books.isEmpty()) {
                %>
                    <ul>
                    <% for (BookInfo b : books) { %>
                        <li>
                            <span><%= b.getTitle() %></span>
                            <form action="admin-delete-book" method="post" style="display:inline; margin:0;">
                                <input type="hidden" name="bookId" value="<%= b.getId() %>">
                                <button type="submit" class="delete-btn">מחק</button>
                            </form>
                        </li>
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