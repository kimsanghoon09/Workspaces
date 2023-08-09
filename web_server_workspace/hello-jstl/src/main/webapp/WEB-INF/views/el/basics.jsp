<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
	EL 
	- Expression Language 
	- jsp에 변수의 내용을 쉽게 출력하기 위한 문법 (el-api.jar) 
	
	- ${내장객체}
	- 내장객체 목록
	1. 각 scope별 context객체의 속성(map)
		- pageScope
		- requestScope
		- sessioScope
		- applicationScope
		- scope를 생략하면, page - request - session - application 순으로 속성을 조회
	2. 사용자입력값(map) param, paramValues
	3. 요청헤더정보(map) header, headerValues
	4. 쿠키(map) cookie
	5. 포인터객체 pageContext

 --%>
<%
	// jsp에서 사용가능한 context객체
	pageContext.setAttribute("book", "개미");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL Basics</title>
</head>
<body>
	<h1>EL Basics</h1>
	
	<h2>Scope별 context객체</h2>
	<h3>request</h3>
	<ul>
		<li>${name}</li>
		<li>${age}</li>
		<li>${married}</li>
		<li>${item}
			<ul>
				<%-- 객체의 속성명이 아닌 getter/setter 활용해 값을 가져온다. (OGNL) --%>
				<li>${item.no}</li>
				<li>${item.name}</li>
				<li>${item.price}</li>
			</ul>
		</li>
		<li>
			${requestScope.names}
			<ul>
				<li>${requestScope.names[0]}</li>
				<li>${requestScope.names[1]}</li>
				<li>${requestScope.names[2]}</li>
				<li>${requestScope.names[3]}</li>
				<li>${requestScope.names[4]}</li><%-- 오류 발생하지 않음 --%>
			</ul>
		</li>
		<li>
			${requestScope.map}
			<ul>
				<li>${requestScope.map.name}</li>
				<li>${requestScope.map.age}</li>
				<li>${requestScope.map.married}</li>
			</ul>
		</li>
		<li>${requestScope.abc}</li><%-- null 출력 안함 --%>
		<li>${requestScope.abc.def}</li><%-- NPE 발생 안함 --%>
	</ul>
	
	<h3>session</h3>
	<ul>
		<li>${book}</li>
		<li>${sessionScope.book}</li>
	</ul>
	<h3>application</h3>
	<ul>
		<li>${applicationScope.movie}</li>
	</ul>
	
	<h2>사용자입력값</h2>
	<ul>
		<li>mode : ${param.mode}</li>
		<li>pname : ${param.pname}</li>
		<li>option1 : ${paramValues.option[0]}</li>
		<li>option2 : ${paramValues.option[1]}</li>
		<c:forEach items="${paramValues.option}" var="option" varStatus="vs">
			<li>${vs.index} ${vs.count} ${option}</li>
		</c:forEach>
	</ul>
	
	<h2>header</h2>
	<ul>
		<li>header : ${header}</li>
		<li>user-agent : ${header['user-agent']}</li> <%-- 속성명에 특수문자 포함시 [] bracket notation 가능 --%>
		<li>referer : ${header.referer}
	</ul>
	
	<h2>cookie</h2>
	<ul>
		<li>cookie : ${cookie}</li>
		<li>JSESSIONID : ${cookie.JSESSIONID.value}
	</ul>
	
	<h2>pageContext</h2>
	<%-- 유일하게 Map이 아닌 실제 PageContext객체를 가리킨다. --%>
	<ul>
		<li>${pageContext.request.contextPath}</li>
		<li><%= ((HttpServletRequest) pageContext.getRequest()).getContextPath() %></li>
		
		<%-- <li>${requestScope.method}</li> --%>
		<li>${pageContext.request.method}</li>
		<li>${pageContext.session.id}</li>
		<li><%= request.getMethod() %></li>
	</ul>
	
	



	<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
</body>
</html>