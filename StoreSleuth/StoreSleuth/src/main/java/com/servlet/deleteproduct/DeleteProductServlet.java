package com.servlet.deleteproduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/deleteproduct")
public class DeleteProductServlet extends HttpServlet {

    // create the query
    private static final String DELETE_QUERY = "DELETE FROM products WHERE foodpoint = ? AND pname = ?;";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set Content type
        res.setContentType("text/html");
        // read the form values
        String foodpoint = req.getParameter("foodpoint");
        String pname = req.getParameter("pname");

        // load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // create the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///storesleuth", "root", "v678reddy543");
             PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {

            if (foodpoint != null && pname != null && !pname.isEmpty()) {
                // set the values
                ps.setString(1, foodpoint);
                ps.setString(2, pname);

                // execute the query
                int rowsAffected = ps.executeUpdate();

                // check if the record was deleted
                if (rowsAffected > 0) {
                    res.sendRedirect("storemain.jsp");
                } else {
                    pw.println("No record found to delete");
                }
            } else {
                pw.println("Foodpoint or product name is missing");
            }
        } catch (SQLException se) {
            pw.println(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            pw.println(e.getMessage());
            e.printStackTrace();
        }

        // close the stream
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}