<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name.app.MyBooksServlet.Book" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>הספרים שלי</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        input { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #28a745; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; width: 100%; }
        button:hover { background-color: #218838; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: right; }
        th { background-color: #007BFF; color: white; }
        .logout { display: inline-block; margin-top: 20px; color: #dc3545; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>הספרים האישיים של: <%= session.getAttribute("username") %></h2>

    <h3>הוסף ספר חדש לרשימה שלך</h3>
    <form action="my-books" method="post" accept-charset="UTF-8">
        <label>שם הספר:</label>
        <input type="text" name="title" required>

        <label>מחבר:</label>
        <input type="text" name="author" required>

        <button type="submit">הוסף ספר</button>
    </form>

    <h3 style="margin-top: 30px;">הספרים שלי</h3>
    <table>
        <tr>
            <th>שם הספר</th>
            <th>מחבר</th>
        </tr>
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
                for (Book b : books) {
        %>
        <tr>
            <td><%= b.getTitle() %></td>
            <td><%= b.getAuthor() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="2" style="text-align: center;">אין לך ספרים ברשימה עדיין.</td>
        </tr>
        <% } %>
    </table>

    <a class="logout" href="logout">התנתק מהמערכת</a>
</div>
</body>
</html>