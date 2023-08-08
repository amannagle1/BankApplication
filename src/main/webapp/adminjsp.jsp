<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html->
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

	String id = request.getParameter("id");
	String email = request.getParameter("email");
	String password = request.getParameter("password");

	int id1 = Integer.parseInt(id);
	PreparedStatement ps = con.prepareStatement("insert into admin(id,email,password)values(?,?,?)");
	ps.setInt(1, id1);
	ps.setString(2, email);
	ps.setString(3, password);
	ps.execute();

	out.println("<h1 align='center'>Account Created successfully</h1>");//jspWriter out - (is ref var of jspWriter class)to print the content on browser

	%>
</body>
</html>