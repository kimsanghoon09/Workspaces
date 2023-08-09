<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL - fmt</title>
</head>
<body>
	<h1>JSTL - fmt</h1>
	<h2>숫자</h2>
	<ul>
		<li><fmt:formatNumber value="${no1}" pattern="#.##"/></li><%-- 123.46 --%>
		<li><fmt:formatNumber value="${no1}" pattern="#.#####"/></li><%-- 123.456 --%>
		<li><fmt:formatNumber value="${no1}" pattern="#.00000"/></li><%-- 123.45600 --%>
		<li><fmt:formatNumber value="${no2}" pattern="#,###"/></li><%-- 3,000,000 --%>		
		<li><fmt:formatNumber value="${no2}" type="currency"/></li><%-- ₩3,000,000 --%>
		<li><fmt:formatNumber value="0.15" type="percent" /></li><%-- 15% --%>		
	</ul>
	
	<h2>날짜/시각</h2>
	<ul>
		<%-- SimpleDateFormat 형식문자 참조 --%>
		<li><fmt:formatDate value="${date}" pattern="yyyy/MM/dd"/></li>
		<li><fmt:formatDate value="${datetime}" pattern="yyyy/MM/dd HH:mm:ss"/></li>
	</ul>

</body>
</html>





