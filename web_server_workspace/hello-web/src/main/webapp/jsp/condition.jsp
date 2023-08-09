<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String mode = request.getParameter("mode");
	System.out.println("mode = " + mode);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>분기처리</title>
</head>
<body>
<!-- html주석은 servlet으로 변환되어 client까지 전달된다. -->
<%-- jsp주석은 servlet으로 변환되지 않는다. --%>
<%
	// ㅋㅋㅋㅋㅋㅋ
	
	
	// 응답 출력스트림객체
	// PrintWriter out = response.getWriter();
	// out내장객체
	if("en".equals(mode)){
		// 영어모드
		out.println("<h1>Hello</h1>");
	}
	else {
		// 한글모드 (기본값)
		out.println("<h1>안녕</h1>");
	}
%>

<%
	if("en".equals(mode)){
%>
	<h1>Hello</h1>
<%				
	} else {
%>
	<h1>안녕</h1>
<%		
	}
%>



	<div>
		<button onclick="location.href='/web/jsp/condition.jsp?mode=en';">영어</button>
		<button onclick="location.href='/web/jsp/condition.jsp?mode=ko';">한글</button>
	</div>


	
	

</body>
</html>