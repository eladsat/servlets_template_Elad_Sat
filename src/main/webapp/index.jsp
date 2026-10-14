<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // מפנה אוטומטית לדף ההתחברות בכניסה לאתר
    response.sendRedirect(request.getContextPath() + "/login");
%>