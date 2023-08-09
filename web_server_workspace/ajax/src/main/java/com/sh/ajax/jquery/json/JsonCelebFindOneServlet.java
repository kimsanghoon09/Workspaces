package com.sh.ajax.jquery.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sh.ajax.celeb.model.service.CelebService;
import com.sh.ajax.celeb.model.vo.Celeb;

/**
 * Servlet implementation class JsonCelebFindOneServlet
 */
@WebServlet("/jquery/json/celeb/findOne")
public class JsonCelebFindOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 입력값처리
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("no = " + no);
		
		// 2. 업무로직
		Celeb celeb = celebService.findOne(no);
		System.out.println("celeb = " + celeb);
		
		// 3. 응답처리 (json)
		// 헤더
		response.setContentType("application/json; charset=utf-8");
		// 바디
		new Gson().toJson(celeb, response.getWriter());
		
	}

}







