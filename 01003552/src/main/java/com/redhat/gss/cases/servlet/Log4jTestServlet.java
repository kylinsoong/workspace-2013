package com.redhat.gss.cases.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redhat.gss.cases.log.LogPrint;


@WebServlet("/Log4jTestServlet")
public class Log4jTestServlet extends HttpServlet {

	private static final long serialVersionUID = 6766348308173972664L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		new LogPrint().test();
	}

}
