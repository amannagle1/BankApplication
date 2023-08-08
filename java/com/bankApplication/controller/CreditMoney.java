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

@WebServlet("/creditmoney")
public class CreditMoney extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String balance = req.getParameter("credit");
		double amnt = Double.parseDouble(balance);

		HttpSession hs = req.getSession();
		long acno = (Long) hs.getAttribute("accountnumber");
		int pin = (Integer) hs.getAttribute("pin");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = Helper.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from account where accountnumber=? and pin=?");
			ps.setLong(1, acno);
			ps.setInt(2, pin);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				double dbbal = rs.getDouble("balance");
				double total = dbbal + amnt;
				PreparedStatement preparedStatement = conn
						.prepareStatement("update account set balance=? where accountnumber=? and pin=?");
				preparedStatement.setDouble(1, total);
				preparedStatement.setLong(2, acno);
				preparedStatement.setInt(3, pin);
				preparedStatement.execute();

				PrintWriter pw = resp.getWriter();
				pw.println("<h1 align='center' style = 'color : red'> Amount Credited successfully </h1> ");

				RequestDispatcher rd = req.getRequestDispatcher("customeroption.html");
				rd.include(req, resp);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

}
