package com.sh.app.jstl.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.app.item.domain.Item;

/**
 * Servlet implementation class JstlCoreServlet
 */
@WebServlet("/jstl/core")
public class JstlCoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> names = Arrays.asList("홍길동", "신사임당", "이순신", "강감찬");
		List<Item> items = Arrays.asList(
			new Item(1, "삼성키보드", 20_000),
			new Item(2, "한성키보드", 35_000),
			new Item(3, "레오폴드키보드", 25_000)
		);
		Map<String, Object> map = Map.of(
			"name", "홍길동",
			"age", 33,
			"married", true
		);
		request.setAttribute("names", names);
		request.setAttribute("items", items);
		request.setAttribute("map", map);
		
		request.getRequestDispatcher("/WEB-INF/views/jstl/core.jsp")
			.forward(request, response);
	}

}
