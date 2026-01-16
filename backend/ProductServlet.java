package backend;

import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ProductServlet extends HttpServlet {

    // ========================= GET (LIST / SEARCH / FILTER / EDIT / DELETE) =========================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String deleteId = req.getParameter("delete");
        String editId = req.getParameter("edit");
        String search = req.getParameter("search");
        String categoryFilter = req.getParameter("category");

        // ---------- DELETE ----------
        if (deleteId != null) {
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE id=?")) {

                ps.setInt(1, Integer.parseInt(deleteId));
                ps.executeUpdate();
                res.sendRedirect("ProductServlet");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ---------- EDIT FORM ----------
        if (editId != null) {
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE id=?")) {

                ps.setInt(1, Integer.parseInt(editId));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h3>Edit Product</h3>");
                    out.println("<form method='post' action='ProductServlet'>");

                    out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");

                    out.println("Name: <input name='name' value='" + rs.getString("name") + "'><br>");
                    out.println("SKU: <input name='sku' value='" + rs.getString("sku") + "'><br>");
                    out.println("Category: <input name='category' value='" + rs.getString("category") + "'><br>");
                    out.println("Price: <input name='price' value='" + rs.getDouble("price") + "'><br>");
                    out.println("Quantity: <input name='quantity' value='" + rs.getInt("quantity") + "'><br>");

                    out.println("<button type='submit'>Update</button>");
                    out.println("</form><hr>");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ---------- TABLE (NORMAL / SEARCH / FILTER) ----------
        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps;

            if (categoryFilter != null && !categoryFilter.trim().isEmpty()) {
                ps = con.prepareStatement("SELECT * FROM products WHERE category = ?");
                ps.setString(1, categoryFilter);
            }
            else if (search != null && !search.trim().isEmpty()) {
                ps = con.prepareStatement(
                        "SELECT * FROM products WHERE name LIKE ? OR sku LIKE ? OR category LIKE ?");
                String q = "%" + search + "%";
                ps.setString(1, q);
                ps.setString(2, q);
                ps.setString(3, q);
            }
            else {
                ps = con.prepareStatement("SELECT * FROM products");
            }

            ResultSet rs = ps.executeQuery();

            out.println("<table border='1' width='100%'>");
            out.println("<tr><th>ID</th><th>Name</th><th>SKU</th><th>Category</th><th>Price</th><th>Qty</th><th>Action</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("sku") + "</td>");
                out.println("<td>" + rs.getString("category") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");
                out.println("<td>");
                out.println("<a href='../ProductServlet?edit=" + id + "'>Edit</a> | ");
                out.println("<a href='../ProductServlet?delete=" + id + "' style='color:red;'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================= POST (ADD / UPDATE) =========================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String sku = req.getParameter("sku");
        String category = req.getParameter("category");
        String newCategory = req.getParameter("newCategory");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        try (Connection con = DBConnection.getConnection()) {

            // ---------- If new category entered ----------
            if (newCategory != null && !newCategory.trim().isEmpty()) {
                category = newCategory;

                PreparedStatement cps = con.prepareStatement(
                        "INSERT IGNORE INTO categories(name) VALUES (?)");
                cps.setString(1, newCategory);
                cps.executeUpdate();
            }

            // ---------- ADD ----------
            if (id == null || id.isEmpty()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO products(name, sku, category, price, quantity) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, sku);
                ps.setString(3, category);
                ps.setDouble(4, price);
                ps.setInt(5, quantity);
                ps.executeUpdate();
            }
            // ---------- UPDATE ----------
            else {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE products SET name=?, sku=?, category=?, price=?, quantity=? WHERE id=?");
                ps.setString(1, name);
                ps.setString(2, sku);
                ps.setString(3, category);
                ps.setDouble(4, price);
                ps.setInt(5, quantity);
                ps.setInt(6, Integer.parseInt(id));
                ps.executeUpdate();
            }

            res.sendRedirect("frontend/product.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
