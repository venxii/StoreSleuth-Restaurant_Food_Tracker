package com.servlet.signin;

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

@WebServlet("/signin")
public class SignInServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set Content type
        res.setContentType("text/html");
        //read the form values
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        

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
        	String sql = "select * from storeinfo where username=? and pwd=?";
        	PreparedStatement ps = con.prepareStatement(sql);
        	
        	HttpSession session = req.getSession();
        	
            //set the values
            ps.setString(1, username);
            ps.setString(2, pwd);
            //execute the query
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            	String foodpoint = rs.getString(1);
            	
            	session.setAttribute("username",username);
            	session.setAttribute("pwd", pwd);
            	session.setAttribute("foodpoint",foodpoint);
            	
            	res.sendRedirect("storemain.jsp");
            	
            	
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

