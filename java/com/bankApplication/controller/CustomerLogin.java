package com.bankApplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankapplication1.helper.Helper;
import com.mysql.cj.protocol.Resultset;
@WebServlet("/loginvalidate")
public class CustomerLogin extends HttpServlet {
 @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String accountnumber=req.getParameter("AccountNumber");
	String pin=req.getParameter("pin");
	
	long accountnumber1=Long.parseLong(accountnumber);
	int pin2=Integer.parseInt(pin);
	
	HttpSession httpSession = req.getSession();
	httpSession.setAttribute("accountnumber", accountnumber1);
	httpSession.setAttribute("pin", pin2);
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=Helper.getConnection();
		
		PreparedStatement ps=con.prepareStatement("select * from account where accountnumber=? and pin=?");
		ps.setLong(1, accountnumber1);
		ps.setInt(2, pin2);
		
		ResultSet rs=ps.executeQuery();
		
		if (rs.next()) 
		{
			PrintWriter pout=resp.getWriter();
			pout.println("<h1 align='center'> Login Successfull</h1>");
			RequestDispatcher rd=req.getRequestDispatcher("customeroption.html");
			rd.include(req, resp);
			
		}
		else
		{
			PrintWriter pout=resp.getWriter();
			pout.println("<h1 align='center' style='color:red'>Invalid Credential</h1>");
			RequestDispatcher rd=req.getRequestDispatcher("login.html");
			rd.include(req, resp);
		}
	} 
	catch (ClassNotFoundException e) 
	{
		e.printStackTrace();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}
}
