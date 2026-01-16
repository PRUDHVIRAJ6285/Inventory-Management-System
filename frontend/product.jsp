<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
    <link rel="stylesheet" href="/inventory/frontend/css/styles.css">
</head>
<body>

<header>
    <h1>Product Management</h1>
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
        <h2>Add Product</h2>

        <form action="../ProductServlet" method="post" class="form-box">
            <label>Name</label>
            <input type="text" name="name" required>

            <label>SKU</label>
            <input type="text" name="sku" required>

              <label>Category</label>
             <select name="category" id="categoryDropdown">
              <option value="">-- Select Category --</option>
             </select>

             <p style="margin:8px 0;">OR add new category:</p>

            <input type="text" name="newCategory" id="newCategory" placeholder="Enter new category">

            <label>Price</label>
            <input type="number" name="price" step="0.01" required>

            <label>Quantity</label>
            <input type="number" name="quantity" required>

            <button type="submit" class="btn-primary">Add Product</button>
        </form>

        <hr>

        <h2>Product List</h2>

        <!-- Filter dropdown -->
        <select id="filterCategory" onchange="filterProducts()" style="padding:8px;">
            <option value="">All Categories</option>
        </select>

        <br><br>

        <!-- Search box -->
        <input type="text" id="searchBox"
               placeholder="Search by name, SKU, category..."
               style="padding:10px; width:300px; border-radius:6px; border:1px solid #ccc;"
               onkeyup="searchProducts()">

        <br><br>

        <div id="productData">Loading...</div>
    </main>
</div>

<script>
function loadProducts() {
    fetch("../ProductServlet")
        .then(res => res.text())
        .then(data => {
            document.getElementById("productData").innerHTML = data;
        });
}

function searchProducts() {
    let q = document.getElementById("searchBox").value;

    fetch("../ProductServlet?search=" + encodeURIComponent(q))
        .then(res => res.text())
        .then(data => {
            document.getElementById("productData").innerHTML = data;
        });
}

function filterProducts() {
    let cat = document.getElementById("filterCategory").value;

    fetch("../ProductServlet?category=" + encodeURIComponent(cat))
        .then(res => res.text())
        .then(data => {
            document.getElementById("productData").innerHTML = data;
        });
}

// Load everything on page load
window.onload = function() {
    loadProducts();

    // Fill both dropdowns
    fetch("../CategoryServlet")
      .then(res => res.text())
      .then(data => {
          document.getElementById("categoryDropdown").innerHTML = data;
          document.getElementById("filterCategory").innerHTML += data;
      });
};
</script>

<script>
const dropdown = document.getElementById("categoryDropdown");
const newCat = document.getElementById("newCategory");

newCat.addEventListener("input", function () {
    if (newCat.value.trim() !== "") {
        dropdown.value = "";
        dropdown.removeAttribute("required");
    } else {
        dropdown.setAttribute("required", "required");
    }
});
</script>

</body>
</html>
