package com.sh.web.servlet;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 
 * GET /web/servletLifeCycle.do 
 * 
 * Servlet interface
 *  - GenericServlet abstract class
 *    - HttpServlet abstract class
 *    	- CustomServlet class 
 *    
 * 서블릿 생명주기
 * - 싱글턴패턴(하나의 객체만 생성해서 재사용)으로 관리됨.
 * 1. 첫번째 요청시 객체생성
 * 2. @PostConstruct 메소드 
 * 3. init(ServletConfig) 설정 메소드
 * 
 * 4. GenericServlet#service - 메소드별 핸들러 (doGet/doPost/doPatch/doDelete...)
 * 
 * 5. destroy메소드 호출
 * 6. @PreDestroy 메소드
 * 7. 자원반납
 *
 */
@WebServlet("/servletLifeCycle.do")
public class ServletLifeCycle extends HttpServlet {
	
	public ServletLifeCycle() {
		System.out.println("[생성자 호출!]");
	}
	
	@PostConstruct
	public void postContruct() {
		System.out.println("[@PostConstruct 호출!]");
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("[init호출! " + config + "]");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[doGet 호출!]");
	}
	
	@Override
	public void destroy() {
		System.out.println("[destroy 호출!]");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("[@PreDestroy 호출!]");
	}
	
}
