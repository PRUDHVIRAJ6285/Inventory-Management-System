package backend;

import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SalesChartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection()) {

            // Group sales by month
            PreparedStatement ps = con.prepareStatement(
                "SELECT MONTH(sale_date) AS month, SUM(total_amount) AS total " +
                "FROM sales GROUP BY MONTH(sale_date) ORDER BY month"
            );

            ResultSet rs = ps.executeQuery();

            // Build JSON manually
            StringBuilder labels = new StringBuilder();
            StringBuilder values = new StringBuilder();

            labels.append("[");
            values.append("[");

            boolean first = true;

            while (rs.next()) {
                if (!first) {
                    labels.append(",");
                    values.append(",");
                }

                labels.append("\"Month ").append(rs.getInt("month")).append("\"");
                values.append(rs.getDouble("total"));

                first = false;
            }

            labels.append("]");
            values.append("]");

            String json = "{ \"labels\": " + labels + ", \"values\": " + values + " }";

            out.print(json);

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"labels\":[], \"values\":[]}");
        }
    }
}
