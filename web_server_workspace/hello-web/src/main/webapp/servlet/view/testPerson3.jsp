<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%><%-- page 지시어 --%>    
<%
	// jsp의 scriptlet - java를 작성가능
	/* 여러줄 주석 */
	// jsp내부 어디든지 여러개 작성가능
	// request:HttpServletRequest, response:HttpServletResponse객체를 선언없이 사용가능
	String name = request.getParameter("name");
	String color = request.getParameter("color");
	String animal = request.getParameter("animal");
	String[] foods = request.getParameterValues("food");
	System.out.println("name@jsp = " + name);
	System.out.println("color@jsp = " + color);
	System.out.println("animal@jsp = " + animal);
	if(foods != null)
		System.out.println("food@jsp = " + Arrays.toString(foods));
	
	// servlet이 전달한 데이터 가져오기 - HttpServletRequest속성
	String result = (String) request.getAttribute("result");
	System.out.println("result@jsp = " + result);
	List<String> samples = (List<String>) request.getAttribute("samples");
	System.out.println("samples@jsp = " + samples);
	String[] items = (String[]) request.getAttribute("items");
	System.out.println("items@jsp = " + Arrays.toString(items));
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8'>
	<title>개인취향검사결과 Servlet/JSP</title>
	<link rel='stylesheet' href='/web/css/testPerson.css'/>
</head>
<body>
	<h1>개인취향검사결과 Servlet/JSP</h1>
	<p class='name'>이름 : <%= name %></p><%-- 스크립틀릿 출력식 --%>
	<p class='color'>선호 색상: <%= color %></p>
	<p class='animal'>선호 동물 : <%= animal %></p>
	<p class='food'>선호 음식 : <%= foods != null ? String.join(" / ", foods) : "없음" %></p>
	<h2><%= result %></h2>
	<h3>오늘의 아이템</h3>
	<ul>
		<li><%= samples.get(0) %></li>
		<li><%= samples.get(1) %></li>
		<li><%= samples.get(2) %></li>
		<li><%= items[0] %></li>
		<li><%= items[1] %></li>
		<li><%= items[2] %></li>
	</ul>
	<!-- 선글라스, 선크림, 수영복 -->
</body>
</html>