package backend;

import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT name FROM categories ORDER BY name")) {

            out.println("<option value=''>-- Select Category --</option>");

            while (rs.next()) {
                String cat = rs.getString("name");
                out.println("<option value='" + cat + "'>" + cat + "</option>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
