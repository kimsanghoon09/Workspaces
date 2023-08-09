package com.sh.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestPersonServlet2 extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 0. 사용자입력값 인코딩처리
		req.setCharacterEncoding("utf-8");
		// 1. 사용자입력값 처리
		String name = req.getParameter("name"); // <input name="name"/>
		String color = req.getParameter("color");
		String animal = req.getParameter("animal");
		String[] foods = req.getParameterValues("food");
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		if(foods != null)
			System.out.println("food = " + Arrays.toString(foods));
		
		// 2. 응답 html 처리
		resp.setContentType("text/html; charset=utf-8"); // MIME타입 지정
		// 텍스트블럭 jdk15
		String html = """
			<!doctype html>
			<html>
			<head>
				<meta charset='utf-8'>
				<title>개인취향검사결과 POST</title>
				<link rel='stylesheet' href='/web/css/testPerson.css'/>
			</head>
			<body>
				<h1>개인취향검사결과 POST</h1>
				<p class='name'>이름 : %s</p>
				<p class='color'>선호 색상: %s</p>
				<p class='animal'>선호 동물 : %s</p>
				<p class='food'>선호 음식 : %s</p>
			</body>
			</html>
		""".formatted(name, color, animal, foods != null ? String.join(" / ", foods) : "없음");
		
		PrintWriter out = resp.getWriter();
		out.print(html); // 응답 http message body에 작성
	}
}




