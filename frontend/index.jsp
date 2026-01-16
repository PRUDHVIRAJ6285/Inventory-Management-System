<%@ page language="java" %>
<%
    // If already logged in, go to dashboard
    if (session.getAttribute("user") != null) {
        response.sendRedirect("frontend/dashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Inventory System</title>
    <link rel="stylesheet" href="frontend/css/styles.css">
</head>
<body class="login-page">

    <div class="login-container">
        <h2>Inventory Management System</h2>

        <!-- NORMAL FORM SUBMIT -->
        <form action="LoginServlet" method="post" class="form-box">

            <label>Username</label>
            <input type="text" name="username" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <!-- IMPORTANT: submit button -->
            <button type="submit" class="btn-primary">Login</button>
        </form>

        <!-- Error message -->
        <p style="color:red; text-align:center;">
            <%= request.getParameter("error") != null ? "Invalid username or password" : "" %>
        </p>
    </div>

</body>
</html>
