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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.sh.ajax.celeb.model.service.CelebService;
import com.sh.ajax.celeb.model.vo.Celeb;
import com.sh.ajax.celeb.model.vo.CelebType;

/**
 * Servlet implementation class JsonCelebEnrollServlet
 */
@WebServlet("/jquery/json/celeb/enroll")
public class JsonCelebEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 0. MultipartRequest객체 생성
		String saveDirectory = getServletContext().getRealPath("/images");
		int maxPostSize = 1024 * 1024 * 10; 
		String encoding = "utf-8";
		FileRenamePolicy policy = new DefaultFileRenamePolicy();
		MultipartRequest multiReq = 
			new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		// 1. 사용자입력값 처리
		String name = multiReq.getParameter("name");
		String type = multiReq.getParameter("type");
		CelebType celebType = CelebType.valueOf(type);
		String profile = multiReq.getFilesystemName("profile"); // 저장된 파일명 
		Celeb celeb = new Celeb(0, name, profile, celebType);
		System.out.println(celeb);
		
		// 2. 업무로직
		int result = celebService.insertCeleb(celeb);
		
		// 3. 응답처리 - 비동기식 POST요청은 redirect없이 결과값을 json으로 전송
		response.setContentType("application/json; charset=utf-8");
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "성공");
		map.put("message", "성공적으로 셀럽을 등록했습니다.");
		map.put("celeb", celeb);
		new Gson().toJson(map, response.getWriter()); // 응답메세지에 src를 json문자열로 변환해 출력
	}

}




