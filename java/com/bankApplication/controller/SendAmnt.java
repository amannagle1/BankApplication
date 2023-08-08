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

@WebServlet("/send")
public class SendAmnt extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accno = req.getParameter("account");
		String amnt = req.getParameter("rs");

		// Receiver  accno
		long accountNumber2 = Long.parseLong(accno);
		double rs = Double.parseDouble(amnt);

		// sender  accno 
		HttpSession hs = req.getSession();
		long accountNumber1 = (Long) hs.getAttribute("accountnumber");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = Helper.getConnection();
			PreparedStatement ps1 = con.prepareStatement("select * from account where accountnumber=?");
			ps1.setLong(1, accountNumber1);
			ResultSet resultSet1 = ps1.executeQuery();

			PreparedStatement ps2 = con.prepareStatement("select * from account where accountnumber=?");
			ps2.setLong(1, accountNumber2);
			ResultSet resultSet2 = ps2.executeQuery();

			if (resultSet2.next()) {
				resultSet1.next();
				double dbbal1 = resultSet1.getDouble("balance");
				double bdbal2 = resultSet2.getDouble("balance");
				if (rs < dbbal1) {
					double total1 = dbbal1 - rs;
					double total2 = bdbal2 + rs;
					ps2 = con.prepareStatement("update account set balance=? where accountnumber=?");
					ps2.setDouble(1, total2);
					ps2.setLong(2, accountNumber2);
					ps2.execute();

					ps1 = con.prepareStatement("update account set balance=? where accountnumber=?");
					ps1.setDouble(1, total1);
					ps1.setLong(2, accountNumber1);
					ps1.execute();

					PrintWriter pw = resp.getWriter();
					pw.println("<h1 align='center' style = 'color : red'> Amount send successfully </h1> ");

					RequestDispatcher rd = req.getRequestDispatcher("customeroption.html");
					rd.include(req, resp);

				} else {
					// insufficient bal
					PrintWriter pw = resp.getWriter();
					pw.println("<h1 align='center' style = 'color : red'> Insufficient balance to send </h1> ");

					RequestDispatcher rd = req.getRequestDispatcher("customeroption.html");
					rd.include(req, resp);
				}

			} else {
				// accounnumber invalid
				PrintWriter pw = resp.getWriter();
				pw.println("<h1 align='center' style = 'color : red'> Invalid account number </h1> ");

				RequestDispatcher rd = req.getRequestDispatcher("send.html");
				rd.include(req, resp);
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
