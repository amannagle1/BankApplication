<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.bankapplication1.helper.Helper"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = Helper.getConnection();
	HttpSession hs = request.getSession();
	long acno = (Long) hs.getAttribute("accountnumber");
	PreparedStatement ps = con.prepareStatement("select * from account where accountnumber=?");
	ps.setLong(1, acno);
	ResultSet rs = ps.executeQuery();

	if (rs.next()) {
	%>
	<table cellpadding="20px" align="center" border="2px">

		<th>ID</th>
		<th>NAME</th>
		<th>AGE</th>
		<th>ACCOUNT no.</th>
		<th>PIN</th>
		<th>BALANCE</th>
		<th>ADDRESS</th>
		<tr>
			<td><%=rs.getInt(1)%></td>
			<td><%=rs.getString(2)%></td>
			<td><%=rs.getInt(3)%></td>
			<td><%=rs.getLong(4)%></td>
			<td><%=rs.getInt(5)%></td>
			<td><%=rs.getDouble(6)%></td>
			<td><%=rs.getString(7)%></td>
		</tr>

		<%
		}
		%>
	</table>

</body>
</html>