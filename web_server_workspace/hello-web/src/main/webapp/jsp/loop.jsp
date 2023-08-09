<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<String> items = Arrays.asList("수영복", "수경", "수영모");
	int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>반복처리</title>
<style>
table { border: 1px solid black; border-collapse: collapse; margin: 20px 0; }
th, td { border: 1px solid black; padding: 5px; }
</style>
</head>
<body>
	<h1>반복처리</h1>
	
	<h2>준비물</h2>
	<ul>
<%
	for(String item : items) {
%>
		<li><%= item %></li>
<%	
	}
%>
	</ul>
	
	<h2>숫자</h2>
<%
	for(int num : nums) {
%>
	<mark><%= num %></mark>
<%			
	}
%>
	
<%
	String no = request.getParameter("no");
	String prod = request.getParameter("prod");
	String[] options = request.getParameterValues("option");
	System.out.println("no = " + no);
	System.out.println("prod = " + prod);
	System.out.println("options = " + (options != null ? Arrays.toString(options) : null));

%>
	<h2>@실습문제 - 주문정보</h2>
	<%-- 주문내역없이 현재페이지에 접근하는 경우는 table이 노출되어서는 안된다. --%>
	<button onclick="location.href = '/web/jsp/loop.jsp?no=123&prod=아이패드&option=빨강&option=256GB';">주문1</button>
	<button onclick="location.href = '/web/jsp/loop.jsp?no=456&prod=설렁탕&option=다대기많이&option=곱빼기&option=당면사리';">주문2</button>
	
<% 	if (no != null && prod != null) { %>
	<table>
		<tr>
			<th>주문번호</th>
			<td><%= no %></td>
		</tr>
		<tr>
			<th>상품명</th>
			<td><%= prod %></td>
		</tr>
<% 		for(int i = 0; options != null && i < options.length; i++){ %>
		<tr>
			<th>옵션<%= i + 1 %></th>
			<td><%= options[i] %></td>
		</tr>
<% 		} %>
	</table>
<% 	} %>
	
	

</body>
</html>