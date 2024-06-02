package com.servlet.editprice;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/editprice")
public class EditPriceServlet extends HttpServlet {

    // create the query
    private static final String EDITPRICE_QUERY = "UPDATE products SET price = ? WHERE foodpoint = ? AND pname = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set Content type
        res.setContentType("text/html");

        // read the form values
        String foodpoint = req.getParameter("foodpoint");
        String pname = req.getParameter("pname");
        String price = req.getParameter("price");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // load the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create the connection
            con = DriverManager.getConnection("jdbc:mysql:///storesleuth", "root", "v678reddy543");
            ps = con.prepareStatement(EDITPRICE_QUERY);

            if (foodpoint != null && pname != null && price != null && !foodpoint.isEmpty() && !pname.isEmpty() && !price.isEmpty()) {
                // set the values
                ps.setBigDecimal(1, new BigDecimal(price));
                ps.setString(2, foodpoint);
                ps.setString(3, pname);

                // execute the query
                int rowsAffected = ps.executeUpdate();

                // check if the record was updated
                if (rowsAffected > 0) {
                    res.sendRedirect("storemain.jsp");
                } else {
                    pw.println("No record found to update");
                }
            } else {
                pw.println("Foodpoint, product name, or price is missing");
            }
        } catch (ClassNotFoundException e) {
            pw.println("JDBC Driver not found");
            e.printStackTrace(pw);
        } catch (SQLException se) {
            pw.println("Database error: " + se.getMessage());
            se.printStackTrace(pw);
        } finally {
            // close resources
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace(pw);
            }
        }

        // close the stream
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}