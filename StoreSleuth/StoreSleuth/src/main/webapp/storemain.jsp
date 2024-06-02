<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
    Object username = session.getAttribute("username");
    Object foodpoint = session.getAttribute("foodpoint");
    Object pwd = session.getAttribute("pwd");

    if (username == null || foodpoint == null || pwd == null) {
        response.sendRedirect("login.html"); // Redirect to login if session attributes are missing
        return;
    }
%>
<%@ page import = "java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Your Store</title>
    <link rel="stylesheet" href="design/cssfiles/storemain.css">
</head>
<body>
    <div class="maxcontainer">
        <div class="yourcontainer">
            <a href="mainmenu.html">
                <img src="design/images/mainiconnobg.png" alt="storesleuth">
            </a>
        </div>
        <div class="card1 justify-content-center">Welcome to</div>
        <div class="card2 justify-content-center">StoreSleuth</div>
        <div class="card3 justify-content-center"><% out.print(username); %></div>
        <div class="btncontainer">
            <a href="storeaddproducts.html">
                <button class="btn justify-content-center">Add your product</button>
            </a>
        </div>
        <div class="btncontainer">
            <a href="editpage.html">
                <button class="btn justify-content-center">Edit your product</button>
            </a>
        </div>
    </div>
    
    <div class="product-container">
        <table style="width:100%">
            <tr>
                <td><div class="card32">Your Products :</div></td>
            </tr>
            <%
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql:///storesleuth", "root", "v678reddy543");
                    String sql = "SELECT pname, price, avail, imagelink FROM products WHERE foodpoint = ?";
                    ps = con.prepareStatement(sql);
                    ps.setObject(1, foodpoint); // Set the parameter before executing
                    rs = ps.executeQuery();
                    
                    if (!rs.next()) {
                        out.println("<tr><td>No records found in the table</td></tr>");
                    } 
                    else {
                    	%>
                    	<tr>
                    	<%
                        do {
            %>
                            <td>
                                <div class="cardsmall">
                                    <div class="foodimage">
                                        <img src="<%= rs.getString("imagelink") %>" alt="Product Image">
                                    </div>
                                    <div class="fooddetails">
                                    	
                                        <div class="foodname"><%= rs.getString("pname") %></div>
                                        <div class="foodprice"><%= rs.getString("price") %></div>
                                        <div class="foodavail"><%= rs.getString("avail") %></div>
                                    </div>
                                </div>
                            </td>
            <%
                        } while (rs.next());
                    	%></tr><%
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Print the stack trace for debugging
                } finally {
                    if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
            %>
        </table>
    </div>
</body>
</html>
