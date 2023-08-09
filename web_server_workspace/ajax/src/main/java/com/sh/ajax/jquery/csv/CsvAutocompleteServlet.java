package com.sh.ajax.jquery.csv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sh.ajax.classmate.model.service.ClassmateService;
import com.sh.ajax.classmate.model.vo.Classmate;

/**
 * Servlet implementation class CsvAutocompleteServlet
 */
@WebServlet("/jquery/csv/autocomplete")
public class CsvAutocompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ClassmateService classmateService = new ClassmateService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		String term = request.getParameter("term");
		System.out.println("term = " + term);
		
		// 2. 업무로직 select * from classmate where name like '%김%'
		List<Classmate> results = classmateService.findByName(term);
		System.out.println(results);
		
		// 3. 응답처리
		response.setContentType("text/csv; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(results != null && !results.isEmpty()) {
			for(int i = 0; i < results.size(); i++) {
				Classmate classmate = results.get(i);
				out.append(classmate.getName());
				if(i != results.size() - 1) 
					out.append("$");
			}
		}
	}

}



