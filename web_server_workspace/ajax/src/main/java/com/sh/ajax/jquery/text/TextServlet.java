package com.sh.ajax.jquery.text;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TextServlet
 */
@WebServlet("/jquery/text")
public class TextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		response.setCharacterEncoding("utf-8");
		response.getWriter()
			.append("Hello world")
			.append("너의 이름은 " + name + "이고, ")
			.append("나이는 " + age + "살입니다.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		System.out.println("memberId = " + memberId + ", password = " + password);
		
		// 2. 업무로직
		
		// 3. 응답처리
		response.setCharacterEncoding("utf-8");
		response.getWriter()
			.append(memberId + "님, 회원가입을 축하합니다. 🎉");
		
	}

}
