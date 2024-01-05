<%@page import="com.jwt.app.entity.Product"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/static/css/listofitems.css" />
<title>listofproducts</title>
</head>
<body>
	<h2>List of Products</h2>


	<!-- <a  href="#"> Click here to apply Filter</a> <br></br> 
 out.print("<td>"+ s.get(c).getId() + "</td>");-->
<c:if test="${empty list}">
    <p>${errorMessage}</p> <%-- Show the error message --%>
</c:if>
<c:if test="${not empty list}">
	<table border="2">

		<th>Name</th>

		<th>Price</th>
		<th>Quantity</th>
		<th>Category</th>
		<%
		List<Product> s = (List<Product>) request.getAttribute("list");
		for (int c = 0; c < s.size(); c++) {
			out.print("<tr> ");

			out.print("<td>" + s.get(c).getProductname() + "</td>");
			out.print("<td>" + s.get(c).getPrice() + "</td>");
			out.print("<td>" + s.get(c).getQuantity() + "</td>");
			out.print("<td>" + s.get(c).getCategory() + "</td>");
			out.print(" <tr>");
		}
		%>
	</table>
	</c:if>
	<h4>
		<a href="/jwt/product/public/homepage">Click here</a> to go back
	</h4>
</body>
</html>