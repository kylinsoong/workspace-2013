package com.kylin.troubleshooting.hang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InfiteLoopServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected Log log = LogFactory.getLog(this.getClass());	
    
    public InfiteLoopServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("-- JVM Hang Lab 2 start");
		
		try{
			if(true) throw new TestException();
		}catch(TestException e){
			log.warn("Unable to fill pool ", e);
		}
		
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		
		System.out.println("-- JVM Hang Lab 2 end");
	}

}
