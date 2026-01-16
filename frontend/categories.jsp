<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Categories</title>
    <link rel="stylesheet" href="/inventory/frontend/css/styles.css">
</head>
<body>

<header>
    <h1>Category Management</h1>
    <div class="user-info">Admin | <a href="../LogoutServlet">Logout</a></div>
</header>

<div class="container">
    <aside class="sidebar">
        <ul>
            <li><a href="dashboard.jsp">Dashboard</a></li>
            <li><a href="product.jsp">Products</a></li>
            <li><a href="categories.jsp">Categories</a></li>
        </ul>
    </aside>

    <main class="content">
        <h2>Add Category</h2>

        <form action="../CategoryServlet" method="post" class="form-box">
            <input type="text" name="name" placeholder="Category name" required>
            <button class="btn-primary">Add Category</button>
        </form>
    </main>
</div>
</body>
</html>
