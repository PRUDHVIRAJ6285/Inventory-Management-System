package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DashboardServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    try {
      Connection con = DBConnection.getConnection();

      // 1. Total Products
      ResultSet rs1 = con.createStatement()
          .executeQuery("SELECT COUNT(*) FROM products");
      rs1.next();
      int totalProducts = rs1.getInt(1);

      // 2. Stock Value
      ResultSet rs2 = con.createStatement()
          .executeQuery("SELECT SUM(price * quantity) FROM products");
      rs2.next();
      double stockValue = rs2.getDouble(1);

      // 3. Low Stock Items
      ResultSet rs3 = con.createStatement()
          .executeQuery("SELECT COUNT(*) FROM products WHERE quantity < 5");
      rs3.next();
      int lowStock = rs3.getInt(1);

      // Send cards HTML
      out.println("<div class='card'>Total Products: <b>" + totalProducts + "</b></div>");
      out.println("<div class='card'>Stock Value: <b>₹" + stockValue + "</b></div>");
      out.println("<div class='card'>Low-Stock Items: <b>" + lowStock + "</b></div>");
      out.println("<div class='card'>Sales Overview (Chart)</div>");

    } catch (Exception e) {
      e.printStackTrace();
      out.println("<div class='card'>Error loading dashboard</div>");
    }
  }
}
