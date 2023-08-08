package com.bankApplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bankapplication1.helper.Helper;

@WebServlet("/debit")
public class DebitMoney extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String amount = req.getParameter("debit");
		double rs = Double.parseDouble(amount);

		HttpSession hs = req.getSession();
		long acno = (Long) hs.getAttribute("accountnumber");
		int pin = (Integer) hs.getAttribute("pin");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = Helper.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from account where accountnumber=? and pin=?");
			ps.setLong(1, acno);
			ps.setInt(2, pin);
			ResultSet set = ps.executeQuery();
			
			if(set.next()) {
				double dbbal = set.getDouble("balance");
				if(rs < dbbal) {
					double total = dbbal - rs;
					ps = con.prepareStatement("update account set balance=? where accountnumber=?");
					ps.setDouble(1, total);
					ps.setLong(2, acno);
					ps.execute();
					
					PrintWriter pw = resp.getWriter();
					pw.println("<h1 align='center' style = 'color : red'> Amount Debited successfully </h1> ");
					
					RequestDispatcher rd = req.getRequestDispatcher("customeroption.html");
					rd.include(req, resp);
				}else {
					PrintWriter pw = resp.getWriter();
					pw.println("<h1 align='center' style = 'color : red'> Insufficient Balance </h1> ");
					
					RequestDispatcher rd = req.getRequestDispatcher("customeroption.html");
					rd.include(req, resp);
				}
				
				
			}

		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
