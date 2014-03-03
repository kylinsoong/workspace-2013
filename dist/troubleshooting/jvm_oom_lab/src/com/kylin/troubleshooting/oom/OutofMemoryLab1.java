package com.kylin.troubleshooting.oom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HashMapTester
 */
public class OutofMemoryLab1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static ArrayList list = new ArrayList();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutofMemoryLab1() {
        super();
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
		  System.out.println("-- OutofMemory test 1 start");
		  Map map = new CustomHashMap();
		  for(int i = 0 ; i < 10 ; i ++){
			  map.put("name" + i, new byte[1048576]);
		  }
		  list.add(map);
		  
		  try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  System.out.println("-- list : " + list.size());
		  getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		  System.out.println("-- OutofMemory test 1 end");
	}

}
