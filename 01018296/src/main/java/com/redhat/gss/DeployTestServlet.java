package com.redhat.gss;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deployTestServlet")
public class DeployTestServlet extends HttpServlet{
	
	public DeployTestServlet() {
		System.out.println("DeployTestServlet Constructed");
	}

	private static final long serialVersionUID = -2182112372348476657L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DeployTestServlet Invoked");
	}

}
