<%
    // Session protection
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Low Stock Alerts</title>
    <link rel="stylesheet" href="/inventory/frontend/css/styles.css">
</head>
<body>

<header>
    <h1>Low Stock Alerts</h1>
    <div class="user-info">
        Admin | <a href="../LogoutServlet">Logout</a>
    </div>
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
        <h2>Low Stock Products</h2>

        <table border="1" width="100%">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>SKU</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody id="alertsData">
                <tr><td colspan="5">Loading...</td></tr>
            </tbody>
        </table>
    </main>
</div>

<script>
fetch("../AlertServlet")
  .then(res => res.text())
  .then(data => {
      document.getElementById("alertsData").innerHTML = data;
  });
</script>

</body>
</html>
