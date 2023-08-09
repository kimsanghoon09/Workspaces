package com.sh.app.jstl.fmt;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JstlFmtServlet
 */
@WebServlet("/jstl/fmt")
public class JstlFmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("no1", 123.456);
		request.setAttribute("no2", 3_000_000);
		
		request.setAttribute("date", Date.valueOf("2023-07-24")); // java.sql.Date		
		request.setAttribute("datetime", new java.util.Date());		
		
		request.getRequestDispatcher("/WEB-INF/views/jstl/fmt.jsp")
			.forward(request, response);
		
	}

}





