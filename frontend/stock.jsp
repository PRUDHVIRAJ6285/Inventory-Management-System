
<%@ page language="java" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Stock Tracking</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>Stock Tracking</h1>
        <div class="user-info">Admin | <a href="../LogoutServlet">Logout</a>
    </header>
    
    <div class="container">
        <aside class="sidebar">
            <ul>
    <li><a href="dashboard.jsp">Dashboard</a></li>
    <li><a href="product.jsp">Products</a></li>
    <li><a href="stock.jsp">Stock</a></li>
    <li><a href="alerts.jsp">Alerts</a></li>
    <li><a href="reports.jsp">Reports</a></li>
    <li><a href="sales.jsp">Sales / Orders</a></li>   <!-- NEW -->
</ul>

        </aside>
        
        <main class="content">
            <h2>Current Stock</h2>
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>SKU</th>
                        <th>Category</th>
                        <th>Price (₹)</th>
                        <th>Quantity</th>
                    </tr>
                </thead>

                <script>
fetch("../StockServlet")
  .then(res => res.text())
  .then(data => {
      document.getElementById("stockData").innerHTML = data;
  });
</script>
                <tbody id="stockData">
    <tr><td colspan="5">Loading...</td></tr>
</tbody>
            </table>
        </main>
    </div>
</body>
</html>
