package com.redhat.gss.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CaseSupport")
public class CaseSupportReproduceServlet extends HttpServlet {

	private static final long serialVersionUID = 6766348308173972664L;
	
	private static Set<HttpSession> set = new HashSet<HttpSession>();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		HttpSession session = req.getSession(true);
		set.add(session);
		System.out.println(Thread.currentThread().getName() +" Sleep, hold session " + session.getId());
		
//		if(null != session) {
//			set.add(session);
//			System.out.println(Thread.currentThread().getName() +" Sleep, hold session " + session.getId());
//		} else {
//			System.out.println(Thread.currentThread().getName() +" Sleep ");
//		}
		
		System.out.println(Thread.currentThread().getName() +" Active session count: " + set.size());
		
		try {
//			Thread.currentThread().sleep(Long.MAX_VALUE);
			Thread.currentThread().sleep(1000 * 60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName() +" Exit ");
	}

}
