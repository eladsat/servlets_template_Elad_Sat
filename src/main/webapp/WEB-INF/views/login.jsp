<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>התחברות</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 400px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        input { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #007BFF; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; width: 100%; margin-top: 10px; }
        button:hover { background-color: #0056b3; }
        .admin-btn { background-color: #6c757d; display: block; text-align: center; color: white; padding: 10px; text-decoration: none; border-radius: 4px; margin-top: 10px; box-sizing: border-box; }
        .admin-btn:hover { background-color: #5a6268; }
        .register-link { display: block; text-align: center; margin-top: 15px; color: #28a745; text-decoration: none; }
        .error { color: red; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="container">
    <h2 style="text-align: center;">התחברות למערכת הספרייה</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="login" method="post">
        <label>שם משתמש:</label>
        <input type="text" name="username" required>

        <label>סיסמה:</label>
        <input type="password" name="password" required>

        <button type="submit">התחבר</button>
    </form>

    <!-- כפתור כניסה לניהול -->
    <a href="admin-login" class="admin-btn">כניסה לניהול 🔐</a>

    <a href="register" class="register-link">אין לך חשבון עדיין? הירשם כאן</a>
</div>
</body>
</html>