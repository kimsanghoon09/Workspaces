package com.sh.ajax.jquery.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sh.ajax.celeb.model.service.CelebService;

/**
 * Servlet implementation class JsonCelebDeleteServlet
 */
@WebServlet("/jquery/json/celeb/delete")
public class JsonCelebDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		int no = Integer.parseInt(request.getParameter("no"));
		
		// 2. 업무로직
		int result = celebService.deleteCeleb(no);
		
		// 3. 응답처리
		response.setContentType("application/json; charset=utf-8");
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "성공");
		map.put("message", "성공적으로 셀럽을 삭제했습니다.");
		new Gson().toJson(map, response.getWriter()); // 응답메세지에 src를 json문자열로 변환해 출력
		
	}

}
