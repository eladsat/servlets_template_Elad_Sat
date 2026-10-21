<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>כניסת מנהל</title>
    <style>
        body { font-family: Arial, sans-serif; direction: rtl; text-align: right; background-color: #f4f4f9; padding: 40px; }
        .container { max-width: 400px; margin: auto; background: white; padding: 25px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        input { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #dc3545; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; width: 100%; }
        button:hover { background-color: #c82333; }
        .error { color: red; margin-bottom: 10px; }
        .back { display: inline-block; margin-top: 15px; color: #007BFF; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>כניסה לאזור מנהל</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="admin-login" method="post">
        <label>סיסמת מנהל:</label>
        <input type="password" name="adminPassword" required>
        <button type="submit">התחבר כמנהל</button>
    </form>
    <a class="back" href="login">← חזרה להתחברות</a>
</div>
</body>
</html>