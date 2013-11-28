package com.redhat.gss.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaseSupportReproduceServlet extends HttpServlet {

	private static final long serialVersionUID = 6766348308173972664L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		System.out.println("re deployment test");
		
		HttpSession session = req.getSession(false);
		
		if(null != session) {
			System.out.println(Thread.currentThread().getName() +" Sleep, hold session " + session.getId());
		} else {
			System.out.println(Thread.currentThread().getName() +" Sleep ");
		}
		
		
		
		try {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
