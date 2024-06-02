<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
    Object pname = session.getAttribute("pname");
    Object price = session.getAttribute("price");
    Object avail = session.getAttribute("avail");
    Object imagelink = session.getAttribute("imagelink");

    if (pname == null) {
        response.sendRedirect("studentsearch1.html"); // Redirect to login if session attributes are missing
        return;
    }
%>
    <%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>StoreSlueth</title>
    <link rel="stylesheet" href="design/cssfiles/studentsearch1style.css">
    
</head>
<body>
    <div class="grid-container1">
        <div class="item1">
           <div class="yourcontainer">
            <a href = "mainmenu.html">
                <img src="design/images/mainicon.png" alt="a cat">
            </a>
          </div>
        </div>
        <div class="item2">
            <form action = "search" class="search-box">
                <input type="text" placeholder=" "name = "pname">
                <button type="reset">
                </button>
            </form>
        </div>
        <div class="item3">
            <p hidden>1</p>
        </div>
        <div class="item4">
            <div class="card">
            <div class="slideshow-container">
    
                <div class="mySlides fade">
                  <img src="design/images/shildeshow/foodstreet.png" alt = "Food Street" style="width:100%">
                </div>
                
                <div class="mySlides fade">
                  <img src="design/images/shildeshow/ab2retail.png" alt="AB-2 Retail" style="width:100%">
                </div>
                
                <div class="mySlides fade">
                  <img src="design/images/shildeshow/rp.png" alt = "Rock Plaza" style="width:100%">
                </div>
    
                <div class="mySlides fade">
                    <img src="design/images/shildeshow/lh1retail.png" alt = "LH-1 Retail" style="width:100%">
                  </div>
                  
                  <div class="mySlides fade">
                    <img src="design/images/shildeshow/maggihotspot.png" alt="Maggi Hotspot" style="width:100%">
                  </div>
                  
                  <div class="mySlides fade">
                    <img src="design/images/shildeshow/ab1retail.png" alt="AB-1 Retail" style="width:100%">
                  </div>
                
                <a class="prev" onclick="plusSlides(-1)">❮</a>
                <a class="next" onclick="plusSlides(1)">❯</a>
                
                </div>
                <div style="text-align:center">
                  <span class="dot" onclick="currentSlide(1)"></span> 
                  <span class="dot" onclick="currentSlide(2)"></span> 
                  <span class="dot" onclick="currentSlide(3)"></span> 
                  <span class="dot" onclick="currentSlide(4)"></span>
                  <span class="dot" onclick="currentSlide(5)"></span>
                  <span class="dot" onclick="currentSlide(6)"></span>
                </div>
                
                <script>
                let slideIndex = 1;
                showSlides(slideIndex);
                
                function plusSlides(n) {
                  showSlides(slideIndex += n);
                }
                
                function currentSlide(n) {
                  showSlides(slideIndex = n);
                }
                
                function showSlides(n) {
                  let i;
                  let slides = document.getElementsByClassName("mySlides");
                  let dots = document.getElementsByClassName("dot");
                  if (n > slides.length) {slideIndex = 1}    
                  if (n < 1) {slideIndex = slides.length}
                  for (i = 0; i < slides.length; i++) {
                    slides[i].style.display = "none";  
                  }
                  for (i = 0; i < dots.length; i++) {
                    dots[i].className = dots[i].className.replace(" active", "");
                  }
                  slides[slideIndex-1].style.display = "block";  
                  dots[slideIndex-1].className += " active";
                }
                </script>
                
        </div>
        </div>
    </div>
    
    
	<div class="product-container">
        <table style="width:100%">
            <%
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql:///storesleuth", "root", "v678reddy543");
                    String sql = "SELECT pname, price, avail, imagelink FROM products WHERE pname = ?";
                    ps = con.prepareStatement(sql);
                    ps.setObject(1, pname); // Set the parameter before executing
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