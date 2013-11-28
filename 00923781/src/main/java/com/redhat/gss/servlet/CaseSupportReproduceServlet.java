package com.redhat.gss.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CaseSupportReproduceServlet extends HttpServlet {

	private static final long serialVersionUID = 6766348308173972664L;
	
	private static int cur = 1;
	
	private static final Logger logger = Logger.getLogger(CaseSupportReproduceServlet.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		for(int i = 0 ; i < 2000 ; i ++){
			logger.info(cur + " " + i + " American International Assurance Company Limited, Shanghai Branch server log loss reproduce");
		}
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cur++;
	}

}
