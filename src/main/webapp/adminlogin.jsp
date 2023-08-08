<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
</head>
<body>
<%
	String email = request.getParameter("email");
	String pass = request.getParameter("password");

	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
	PreparedStatement ps = conn.prepareStatement("select * from admin where email=? && password=?");

	ps.setString(1, email);
	ps.setString(2, pass);

	ResultSet rs = ps.executeQuery();
	%>

	<%
	if (rs.next()) {
		Statement statement = conn.createStatement();
		ResultSet rs1 = statement.executeQuery("select * from account");
	%>
	<table cellpadding="20px" align="center" border="2px">

		<th>ID</th>
		<th>NAME</th>
		<th>AGE</th>
		<th>account number</th>
		<th>balance</th>
		<th>address</th>
		<%
		while (rs1.next()) {
		%>
		<tr>
			<td><%=rs1.getInt(1)%></td>
			
			<td><%=rs1.getString(2)%></td>
			<td><%=rs1.getInt(3)%></td>
			<td><%=rs1.getLong(4)%></td>
			<td><%=rs1.getDouble(6)%></td>
			<td><%=rs1.getString(7)%></td>

		</tr>

		<%
		}
		} else {
			PrintWriter pout = response.getWriter();
			pout.println("<h1 align='center' style = 'color : red'> Invalid Credintials </h1> ");
			RequestDispatcher rd = request.getRequestDispatcher("adminlogin.html");
			rd.include(request, response);
		}
		%>
	</table>


</body>

</body>
</html>