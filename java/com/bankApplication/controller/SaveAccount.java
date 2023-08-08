package com.bankApplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveaccount")
public class SaveAccount extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id=req.getParameter("Id");
		String name=req.getParameter("Name");		
		String age=req.getParameter("Age");		
		String pin=req.getParameter("Pin");		
		String address=req.getParameter("Address");		
		
		int id1=Integer.parseInt(id);
		int age1=Integer.parseInt(age);
		int pin1=Integer.parseInt(pin);
		
		Random r=new Random();
		
		long accountnumber=r.nextLong(100000000000l);
		double balance=5000.00;
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
		PreparedStatement ps=con.prepareStatement("insert into account(id,name,age,accountnumber,pin,balance,address) values(?,?,?,?,?,?,?)");
		ps.setInt(1, id1);
		ps.setString(2, name);
		ps.setInt(3, age1);
		ps.setLong(4,accountnumber);
		ps.setInt(5, pin1);
		ps.setDouble(6, balance);
		ps.setString(7, address);
		
		ps.execute();
		} 
		catch (ClassNotFoundException e) {
		e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pout=resp.getWriter();
		pout.print("<h1 align='center' style='color:green'>Account created successfully</h>");
		RequestDispatcher rd=req.getRequestDispatcher("login.html");
		rd.include(req, resp);
	}
}
