package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class StockServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    try {
      Connection con = DBConnection.getConnection();
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery("SELECT * FROM products");

      while (rs.next()) {
        int qty = rs.getInt("quantity");

        // Highlight low stock
        String rowStyle = qty < 5 ? "style='background:#ffe6e6;'" : "";

        out.println("<tr " + rowStyle + ">");
        out.println("<td>" + rs.getString("name") + "</td>");
        out.println("<td>" + rs.getString("sku") + "</td>");
        out.println("<td>" + rs.getString("category") + "</td>");
        out.println("<td>" + rs.getDouble("price") + "</td>");
        out.println("<td>" + qty + "</td>");
        out.println("</tr>");
      }

    } catch (Exception e) {
      e.printStackTrace();
      out.println("<tr><td colspan='5'>Error loading stock</td></tr>");
    }
  }
}
