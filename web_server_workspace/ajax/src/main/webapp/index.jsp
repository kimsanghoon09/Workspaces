<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello Ajax</title>
</head>
<body>
	<h1>Hello Ajax</h1>
	
	<h2>JS</h2>
	<a href="<%= request.getContextPath() %>/javascript/">GET | POST</a>
	
	<h2>jQuery</h2>
	<ul>
		<li><a href="<%= request.getContextPath() %>/jquery/text/text.jsp">text</a></li>
		<li><a href="<%= request.getContextPath() %>/jquery/html/html.jsp">html</a></li>
		<li><a href="<%= request.getContextPath() %>/jquery/csv/csv.jsp">csv</a></li>
		<li><a href="<%= request.getContextPath() %>/jquery/xml/xml.jsp">xml</a></li>
		<li><a href="<%= request.getContextPath() %>/jquery/json/json.jsp">json</a></li>
	</ul>
	
	<h2>기타</h2>
	<ul>
		<li><a href="<%= request.getContextPath() %>/formdata/formdata.jsp">FormData</a></li>
	</ul>
	
</body>
</html>