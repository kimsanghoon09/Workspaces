package com.sh.ajax.jquery.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sh.ajax.celeb.model.service.CelebService;
import com.sh.ajax.celeb.model.vo.Celeb;

/**
 * Servlet implementation class JsonCelebFindAllServlet
 */
@WebServlet("/jquery/json/celeb/findAll")
public class JsonCelebFindAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		
		// 2. 업무로직 
		List<Celeb> celebs = celebService.findAll();
		System.out.println("celebs = " + celebs);
		
		// 3. 응답처리 json (java -> json)
		// 헤더
		response.setContentType("application/json; charset=utf-8");
		
		// 바디
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(celebs);
//		System.out.println("jsonStr = " + jsonStr);
//		response.getWriter().append(jsonStr);
		
		new Gson().toJson(celebs, response.getWriter());
	}

}





