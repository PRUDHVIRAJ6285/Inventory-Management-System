
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
    <title>Reports & Analytics</title>
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <header>
        <h1>Reports & Analytics</h1>
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
            <h2>Inventory Reports</h2>
            <canvas id="salesChart" width="400" height="200"></canvas>
            
          <script>
fetch("../ReportServlet")
  .then(res => res.json())
  .then(chartData => {

    const ctx = document.getElementById('salesChart').getContext('2d');

    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: chartData.labels,
        datasets: [{
          label: 'Total Quantity by Category',
          data: chartData.data,
          backgroundColor: '#1abc9c'
        }]
      }
    });

  })
  .catch(err => console.error("Chart load error:", err));
</script>

        </main>
    </div>
</body>
</html>
