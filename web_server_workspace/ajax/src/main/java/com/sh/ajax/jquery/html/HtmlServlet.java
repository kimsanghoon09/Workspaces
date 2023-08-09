package com.sh.ajax.jquery.html;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.ajax.celeb.model.service.CelebService;
import com.sh.ajax.celeb.model.vo.Celeb;

/**
 * Servlet implementation class HtmlServlet
 */
@WebServlet("/jquery/html")
public class HtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 입력값 처리
		
		// 2. 업무로직
		List<Celeb> celebs = celebService.findAll();
		System.out.println("celebs = " + celebs);
		
		request.setAttribute("celebs", celebs);
		
		// 3. 응답처리 - jsp
		request.getRequestDispatcher("/WEB-INF/views/jquery/html/celeb.jsp")
			.forward(request, response);
	}
}
