package com.sh.ajax.jquery.csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.ajax.celeb.model.service.CelebService;
import com.sh.ajax.celeb.model.vo.Celeb;

/**
 * Servlet implementation class CsvServlet
 */
@WebServlet("/jquery/csv")
public class CsvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CelebService celebService = new CelebService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		
		// 2. 업무로직
		List<Celeb> celebs = celebService.findAll();
		
		// 3. 응답처리
		response.setContentType("text/csv; charset=utf-8");
		PrintWriter out = response.getWriter();
		for(int i = 0; i < celebs.size(); i++) {
			Celeb c = celebs.get(i);
			out.append(String.format("%s,%s,%s,%s", c.getNo(), c.getName(), c.getProfile(), c.getCelebType()));
			if(i != celebs.size() - 1)
				out.append("\n");
		}
	}

}




