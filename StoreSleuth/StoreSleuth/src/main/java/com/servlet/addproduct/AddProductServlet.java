package com.servlet.addproduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/addproducts")
public class AddProductServlet extends HttpServlet{

    //create the query
    private static final String INSERT_QUERY ="INSERT INTO PRODUCTS(FOODPOINT, PNAME, PRICE, AVAIL) VALUES(?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set Content type
        res.setContentType("text/html");
        //read the form values
        String foodpoint = req.getParameter("foodpoint");
        String pname = req.getParameter("pname");
        String price = req.getParameter("price");
        String avail = req.getParameter("avail");

        //load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //create the connection
        try{
        	Connection con = DriverManager.getConnection("jdbc:mysql:///storesleuth","root","v678reddy543");
        	
        	//Adding data from HTML Form to Database
        	PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
        	
            //set the values
            ps.setString(1, foodpoint);
            ps.setString(2, pname);
            ps.setString(3, price);
            ps.setString(4, avail);

            //execute the query
            int rs = ps.executeUpdate();
            
            //To fetch data from database to display in HTML pages
            
            if(rs==0) {
            	pw.println("Record hasn't been stored into Database");
            	
            }else {
            	pw.println("Record stored into database");
            }
        }catch(SQLException se) {
            pw.println(se.getMessage());
            se.printStackTrace();
        }catch(Exception e) {
            pw.println(e.getMessage());
            e.printStackTrace();
        }

        //close the stream
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}