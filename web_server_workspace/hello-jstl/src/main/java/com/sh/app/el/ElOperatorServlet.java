package com.sh.app.el;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.item.domain.Item;

/**
 * Servlet implementation class ElOperatorServlet
 */
@WebServlet("/el/operator")
public class ElOperatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("big", 100);
		request.setAttribute("small", 30);
		request.setAttribute("str1", "안녕");
		request.setAttribute("str2", new String("안녕"));
		request.setAttribute("item1", new Item(123, "메론맛사탕", 300));
		request.setAttribute("item2", new Item(123, "메론맛사탕", 300));
		request.setAttribute("list1", null);
		request.setAttribute("list2", new ArrayList<>());
		request.setAttribute("list3", Arrays.asList("딸기", "토마토", "복숭아"));
		
		request.getRequestDispatcher("/WEB-INF/views/el/operator.jsp")
			.forward(request, response);
	}

}
