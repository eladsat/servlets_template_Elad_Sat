<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>הרשמה למערכת הספרייה</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 50px; }
        .container { max-width: 400px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #4CAF50; color: white; padding: 10px; border: none; width: 100%; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #45a049; }
        .error { color: red; margin-bottom: 10px; }
        a { text-decoration: none; color: #007BFF; display: block; margin-top: 15px; text-align: center; }
    </style>
</head>
<body>
<div class="container">
    <h2>הרשמה למערכת הספרייה</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="register" method="post">
        <label>שם משתמש:</label>
        <input type="text" name="username" required>

        <label>סיסמה:</label>
        <input type="password" name="password" required>

        <button type="submit">הירשם</button>
    </form>

    <a href="login">כבר יש לך חשבון? התחבר כאן</a>
</div>
</body>
</html>