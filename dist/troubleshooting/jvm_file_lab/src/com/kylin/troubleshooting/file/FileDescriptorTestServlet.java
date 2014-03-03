package com.kylin.troubleshooting.file;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class testServlet
 */
public class FileDescriptorTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected Log log = LogFactory.getLog(this.getClass());	
    /**
     * Default constructor. 
     */
    public FileDescriptorTestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-- File Descriptor test start");
		int open = 0;
		String str = request.getParameter("OPEN");
		
		System.out.println("open file number: " + str);
		
		if(str != null)open = Integer.parseInt(str);
		TestClass.openFile(open);
		getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		System.out.println("-- File Descriptor test end");
	}

}
