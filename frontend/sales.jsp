<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Sales / Orders</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header>
    <h1>Sales / Place Order</h1>
    <div class="user-info">Admin | <a href="../LogoutServlet">Logout</a></div>
</header>

<div class="container">
    <aside class="sidebar">
        <ul>
            <li><a href="dashboard.jsp">Dashboard</a></li>
            <li><a href="product.jsp">Products</a></li>
            <li><a href="stock.jsp">Stock</a></li>
            <li><a href="alerts.jsp">Alerts</a></li>
            <li><a href="reports.jsp">Reports</a></li>
            <li><a href="sales.jsp">Sales / Orders</a></li>
        </ul>
    </aside>

    <main class="content">
        <h2>Place Sale</h2>

        <form action="../SalesServlet" method="post">
            <label>Product ID</label>
            <input type="number" name="productId" required>

            <label>Quantity</label>
            <input type="number" name="quantity" required>

            <button class="btn-primary">Place Order</button>
        </form>

        <h2>Sales History</h2>
        <div id="salesData">Loading...</div>
    </main>
</div>

<script>
fetch("../SalesServlet")
  .then(res => res.text())
  .then(data => {
      document.getElementById("salesData").innerHTML = data;
  });
</script>

</body>
</html>
