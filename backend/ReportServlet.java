package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ReportServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("application/json");
    PrintWriter out = res.getWriter();

    try {
      Connection con = DBConnection.getConnection();
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT category, SUM(quantity) AS total FROM products GROUP BY category");

      StringBuilder labels = new StringBuilder("[");
      StringBuilder data = new StringBuilder("[");

      boolean first = true;
      while (rs.next()) {
        if (!first) {
          labels.append(",");
          data.append(",");
        }
        labels.append("\"").append(rs.getString("category")).append("\"");
        data.append(rs.getInt("total"));
        first = false;
      }

      labels.append("]");
      data.append("]");

      out.print("{\"labels\":" + labels + ",\"data\":" + data + "}");

      rs.close();
      st.close();
      con.close();

    } catch (Exception e) {
      e.printStackTrace();
      out.print("{\"labels\":[],\"data\":[]}");
    }
  }
}
