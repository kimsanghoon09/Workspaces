package com.sh.ajax.jquery.json;

import java.io.File;
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
 * Servlet implementation class JsonCelebUpdateServlet
 */
@WebServlet("/jquery/json/celeb/update")
public class JsonCelebUpdateServlet extends HttpServlet {
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
		
		// 0. 기존 celeb정보 조회
		int no = Integer.parseInt(multiReq.getParameter("no"));
		Celeb celeb = celebService.findOne(no);
		
		
		// 1. 사용자입력값 처리
		String name = multiReq.getParameter("name");
		String _celebType = multiReq.getParameter("type");
		CelebType celebType = CelebType.valueOf(_celebType);
		String profile = celeb.getProfile(); // 기존값을 대입
		if(multiReq.getFile("profile") != null) {
			profile = multiReq.getFilesystemName("profile"); // 실제 저장된 이름 조회
			
			// 기존파일 제거 
			File delFile = new File(saveDirectory, celeb.getProfile());
			if(delFile.exists())
				delFile.delete();
		}
		Celeb newCeleb = new Celeb(no, name, profile, celebType);
		System.out.println("수정된 celeb = " + newCeleb);
		
		// 2. 업무로직
		int result = celebService.updateCeleb(newCeleb);
		
		// 3. 응답처리 (json)
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> map = new HashMap<>();
		map.put("result", "성공");
		map.put("message", "성공적으로 셀럽정보를 수정했습니다.");
		map.put("celeb", newCeleb);
		new Gson().toJson(map, response.getWriter());
		
	}

}
