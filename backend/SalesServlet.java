package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class SalesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection()) {

            // Show sales history
            PreparedStatement ps = con.prepareStatement(
                "SELECT o.id, p.name, o.quantity, o.total_amount, o.order_date " +
                "FROM orders o JOIN products p ON o.product_id = p.id " +
                "ORDER BY o.id DESC"
            );

            ResultSet rs = ps.executeQuery();

            out.println("<table border='1' width='100%'>");
            out.println("<tr><th>ID</th><th>Product</th><th>Qty</th><th>Total</th><th>Date</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt(1) + "</td>");
                out.println("<td>" + rs.getString(2) + "</td>");
                out.println("<td>" + rs.getInt(3) + "</td>");
                out.println("<td>₹" + rs.getDouble(4) + "</td>");
                out.println("<td>" + rs.getDate(5) + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading sales history");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        int productId = Integer.parseInt(req.getParameter("productId"));
        int qty = Integer.parseInt(req.getParameter("quantity"));

        try (Connection con = DBConnection.getConnection()) {

            // Get product price
            PreparedStatement p1 = con.prepareStatement("SELECT price, quantity FROM products WHERE id=?");
            p1.setInt(1, productId);
            ResultSet rs = p1.executeQuery();

            if (!rs.next()) {
                res.getWriter().println("Invalid product");
                return;
            }

            double price = rs.getDouble("price");
            int available = rs.getInt("quantity");

            if (qty > available) {
                res.getWriter().println("Not enough stock!");
                return;
            }

            double total = price * qty;

            // Insert order
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO orders(product_id, quantity, total_amount, order_date) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, productId);
            ps.setInt(2, qty);
            ps.setDouble(3, total);
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();

            // Reduce stock
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE products SET quantity = quantity - ? WHERE id=?"
            );
            ps2.setInt(1, qty);
            ps2.setInt(2, productId);
            ps2.executeUpdate();

            res.sendRedirect("frontend/sales.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
