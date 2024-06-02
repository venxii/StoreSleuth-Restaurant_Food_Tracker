package com.servlet.studentsearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/search")
public class StudentSearchServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set Content type
        res.setContentType("text/html");
        //read the form values
        String pname = req.getParameter("pname");
        

        //load the jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //create the connection
        try {
        	Connection con = DriverManager.getConnection("jdbc:mysql:///storesleuth","root","v678reddy543");
        	String sql = "select * from products where pname=?";
        	PreparedStatement ps = con.prepareStatement(sql);
        	
        	HttpSession session = req.getSession();
        	
            //set the values
            ps.setString(1, pname);
            //execute the query
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            	String price = rs.getString(1);
            	String avail = rs.getString(2);
            	String imagelink = rs.getString(3);
            	
            	session.setAttribute("pname",pname);
            	session.setAttribute("price",price);
            	session.setAttribute("avail", avail);
            	session.setAttribute("imagelink",imagelink);
            	
            	res.sendRedirect("studentsearch.jsp");
            	
            	
            }else {
            	pw.println("Record not stored into database");
            	
                
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

