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
  <meta charset="UTF-8">

    <title>Dashboard - Inventory System</title>

    <link rel="stylesheet" href="css/styles.css">

    <!-- Chart.js CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<header>
    <h1>Inventory Dashboard</h1>
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
    <li><a href="sales.jsp">Sales / Orders</a></li>   <!-- NEW -->
</ul>

    </aside>

    <main class="content">
        <h2>Overview</h2>

        <!-- Cards -->
        <div id="dashboardData" class="cards">
            <div class="card">Loading...</div>
        </div>

        <!-- Sales Chart -->
        <h2 style="margin-top:30px;">Sales Overview</h2>
        <canvas id="salesChart" height="100"></canvas>
    </main>
</div>

<script>
/* Load dashboard cards */
fetch("../DashboardServlet")
  .then(res => res.text())
  .then(data => {
      document.getElementById("dashboardData").innerHTML = data;
  });

/* Load chart data */
fetch("../SalesChartServlet")
  .then(res => res.json())
  .then(chartData => {

      const ctx = document.getElementById('salesChart').getContext('2d');

      new Chart(ctx, {
          type: 'bar',
          data: {
              labels: chartData.labels,
              datasets: [{
                  label: 'Sales Amount ',
                  data: chartData.values,
                  borderWidth: 1
              }]
          },
          options: {
              responsive: true,
              scales: {
                  y: {
                      beginAtZero: true
                  }
              }
          }
      });

  });
</script>

</body>
</html>
