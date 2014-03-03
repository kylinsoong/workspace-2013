package com.kylin.troubleshooting.hang;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CountServlet extends HttpServlet{

	private static final long serialVersionUID = 5844030261780594508L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("-- test JVMHang Lab 1 start");
		int count = Counter.getCount();
		req.getSession().setAttribute("count", count);
		getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
		System.out.println("-- test JVMHang Lab 1 end : " + count);
	}

}
