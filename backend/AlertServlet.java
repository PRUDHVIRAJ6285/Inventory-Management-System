package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AlertServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM products WHERE quantity < 5"
            );

            ResultSet rs = ps.executeQuery();

            out.println("<table class='styled-table' width='100%'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Product</th>");
            out.println("<th>SKU</th>");
            out.println("<th>Category</th>");
            out.println("<th>Price</th>");
            out.println("<th>Quantity</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            boolean found = false;

            while (rs.next()) {
                found = true;

                out.println("<tr style='background:#ffe6e6;'>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("sku") + "</td>");
                out.println("<td>" + rs.getString("category") + "</td>");
                out.println("<td>₹" + rs.getDouble("price") + "</td>");
                out.println("<td><b>" + rs.getInt("quantity") + "</b></td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr>");
                out.println("<td colspan='5' style='text-align:center;'>No low-stock alerts</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

        } catch (Exception e) {
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
