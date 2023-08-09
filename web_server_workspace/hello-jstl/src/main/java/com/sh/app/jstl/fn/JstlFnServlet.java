package com.sh.app.jstl.fn;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JstlFnServlet
 */
@WebServlet("/jstl/fn")
public class JstlFnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("str1", "Hello world, Hello jstl");
		request.setAttribute("str2", "Hello el");
		request.setAttribute("str3", "<script>alert('ㅋㅋㅋㅋㅋㅋㅋㅋ 너 XSS해킹당한 거야~');</script>");
		
		request.getRequestDispatcher("/WEB-INF/views/jstl/fn.jsp")
			.forward(request, response);
	}

}
