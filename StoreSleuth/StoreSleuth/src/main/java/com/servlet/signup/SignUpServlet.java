package com.servlet.signup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet{

    //create the query
    private static final String INSERT_QUERY ="INSERT INTO STOREINFO(FOODPOINT, USERNAME, PWD) VALUES(?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set Content type
        res.setContentType("text/hmtl");
        //read the form values
        String foodpoint = req.getParameter("foodpoint");
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
        try{
        	Connection con = DriverManager.getConnection("jdbc:mysql:///storesleuth","root","v678reddy543");
        	
        	//Adding data from HTML Form to Database
        	PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
        	
            //set the values
            ps.setString(1, foodpoint);
            ps.setString(2, username);
            ps.setString(3, pwd);

            //execute the query
            ps.executeUpdate();
            
            //To fetch data from database to display in HTML pages
            String sql = "select * from storeinfo where foodpoint=? and username=? and pwd=? ";
            PreparedStatement ps2 = con.prepareStatement(sql);
            HttpSession session = req.getSession();
            
            ps2.setString(1, foodpoint);
            ps2.setString(2, username);
            ps2.setString(3, pwd);
            
            ResultSet rs = ps2.executeQuery();
            
            if(rs.next()) {
            	
            	session.setAttribute("username",username);
            	session.setAttribute("pwd", pwd);
            	session.setAttribute("foodpoint",foodpoint);
            	
            	res.sendRedirect("storemain.jsp");
            }else {
                pw.println("Record hasn't been stored into Database");
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
