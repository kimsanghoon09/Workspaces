package com.sh.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * - servlet class만들기
 * - doPost 작성
 * - 사용자 입력값 출력
 *
 *
 * - /web
 * - context-path, context-root, app-name
 * - 언제만 context-path를 경로에 포함시켜야 할까?
 * - jsp, html에서는 context-path부터 작성할 것(클라이언트호출)  /web/css/main.css, /web/testPerson.do
 * - xml, java에서는 context-path를 반드시 생략하고 작성할 것.(서버내부사용) /testPerson.do, /servlet/view/testPerson3.jsp
 *
 */
public class TestPersonServlet3 extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 인코딩처리
		req.setCharacterEncoding("utf-8");
		// 2. 사용자입력값 처리
		String name = req.getParameter("name");
		String color = req.getParameter("color");
		String animal = req.getParameter("animal");
		String[] foods = req.getParameterValues("food");
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("fodds = " + Arrays.toString(foods));
		
		// 업무로직(비지니스로직) - DB
		String result = "당신은 ㅋㅋㅋ";
		List<String> samples = Arrays.asList("선글라스", "선크림", "수영복");
		String[] items = {"선글라스", "선크림", "수영복"};
		
		// servlet에서 jsp로 데이터를 전달하는 방법
		// HttpServletRequest 속성으로 저장하고, jsp에서 꺼내쓴다.
		req.setAttribute("result", result); // name:String, value:Object
		req.setAttribute("samples", samples);
		req.setAttribute("items", items);
		
		// 3. jsp위임 - 응답html작성
		// path에서 /는 src/main/webapp디렉토리를 가리킨다.
		RequestDispatcher reqDispatcher = req.getRequestDispatcher("/servlet/view/testPerson3.jsp");
		reqDispatcher.forward(req, resp);
	}
}
