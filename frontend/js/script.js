// ================= DASHBOARD =================
function loadDashboard() {
    fetch("../DashboardServlet")
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("dashboardData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Dashboard error:", err));
}


// ================= STOCK =================
function loadStock() {
    fetch("../StockServlet")
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("stockData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Stock error:", err));
}


// ================= ALERTS =================
function loadAlerts() {
    fetch("../AlertServlet")
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("alertsData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Alerts error:", err));
}


// ================= PRODUCTS =================
function loadProducts() {
    fetch("../ProductServlet")
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("productData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Products error:", err));
}


// ================= SEARCH =================
function searchProducts() {
    const q = document.getElementById("searchBox")?.value || "";

    fetch("../ProductServlet?search=" + encodeURIComponent(q))
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("productData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Search error:", err));
}


// ================= FILTER BY CATEGORY =================
function filterProducts() {
    const cat = document.getElementById("filterCategory")?.value || "";

    fetch("../ProductServlet?category=" + encodeURIComponent(cat))
        .then(res => res.text())
        .then(data => {
            const el = document.getElementById("productData");
            if (el) el.innerHTML = data;
        })
        .catch(err => console.error("Filter error:", err));
}


// ================= PAGE LOAD =================
window.onload = function () {

    if (document.getElementById("dashboardData")) loadDashboard();
    if (document.getElementById("stockData")) loadStock();
    if (document.getElementById("alertsData")) loadAlerts();
    if (document.getElementById("productData")) loadProducts();

};
