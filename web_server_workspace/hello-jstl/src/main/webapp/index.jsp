<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello JSTL</title>
</head>
<body>
	<h1>Hello JSTL</h1>
	
	<h2>EL</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/el/basics?mode=kr&pname=아이폰&option=빨강&option=256GB">EL Basics</a></li>
		<li><a href="${pageContext.request.contextPath}/el/operator">EL 연산자</a></li>
	</ul>
	
	<h2>JSTL</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/jstl/core?lang=en">core</a></li>
		<li><a href="${pageContext.request.contextPath}/jstl/fmt">fmt</a></li>
		<li><a href="${pageContext.request.contextPath}/jstl/fn">functions</a></li>
	</ul>

</body>
</html>