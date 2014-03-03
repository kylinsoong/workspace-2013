package com.kylin.troubleshooting.oom;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThreadLocalTester
 */
public class OutofMemoryLab3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ThreadLocal tl = new ThreadLocal();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutofMemoryLab3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-- OutofMemory test 3 start");
		try{
		  Thread.sleep(1000L);
		}catch(Exception e){
		  
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		System.out.println("-- OutofMemory test 3 end");
	}

}
