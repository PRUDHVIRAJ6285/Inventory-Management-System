package backend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      con = DBConnection.getConnection();

      String sql = "SELECT * FROM users WHERE username=? AND password=?";
      ps = con.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, password);

      rs = ps.executeQuery();

      if (rs.next()) {
        // ✅ Login success
        HttpSession session = req.getSession();
        session.setAttribute("user", username);

        res.sendRedirect("frontend/dashboard.jsp");

      } else {
        // ❌ Login failed
        res.sendRedirect("index.jsp?error=1");
      }

    } catch (Exception e) {
      e.printStackTrace();
      res.getWriter().println("Login error: " + e.getMessage());
    } finally {
      // ✅ Close resources (important for professional code)
      try {
        if (rs != null)
          rs.close();
        if (ps != null)
          ps.close();
        if (con != null)
          con.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
