<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- jstl을 사용하기 위한 선언문. 매 jsp마다 필요. --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL - core</title>
<style>
table {
	border: 1px solid #000;
	border-collapse: collapse;
	margin: 20px 10px;
}
th, td {
	border: 1px solid #000;
	padding: 5px;
}
</style>
</head>
<body>
	<h1>JSTL - core</h1>
	<h2>변수</h2>
	<%-- <% pageContext.setAttribute("no1", 10); %> --%>
	<c:set var="no1" value="100" scope="page"/>
	<c:set var="no2" value="20" scope="page"/>
	<ul>
		<li>no1 : <c:out value="${pageScope.no1}"/></li>
		<li>no1 : ${no1}</li>
	</ul>
	
	<h2>분기처리</h2>
	<h3>c:if</h3>
	<c:if test="${Integer.parseInt(no1) gt Integer.parseInt(no2)}">
		<p>${no1}이 ${no2}보다 큽니다.</p>
	</c:if>
	<c:if test="${Integer.parseInt(no1) lt Integer.parseInt(no2)}">
		<p>${no1}이 ${no2}보다 작습니다.</p>
	</c:if>
	<c:if test="${Integer.parseInt(no1) eq Integer.parseInt(no2)}">
		<p>${no1}와 ${no2}는 같습니다.</p>
	</c:if>
	
	
	<h3>c:choose</h3>
	<c:set var="n" value="<%= (int)(Math.random() * 5 + 1) %>"></c:set>	
	${n}
	<c:choose>
		<c:when test="${n eq 1}">오토바이를 뽑았습니다.</c:when>
		<c:when test="${n eq 2}">권총라이터를 뽑았습니다.</c:when>
		<c:when test="${n eq 3}">오리 인형을 뽑았습니다.</c:when>
		<c:otherwise>꽝!!!! 다음 기회에....</c:otherwise>
	</c:choose>
	
	<h3>@실습문제</h3>
	<c:if test="${param.lang eq 'kr' || empty param.lang}">
		<p>안녕하세요</p>	
	</c:if>
	<c:if test="${param.lang eq 'en'}">
		<p>Hello</p>	
	</c:if>
	
	<c:choose>
		<c:when test="${param.lang eq 'en'}">
			<p>Hello</p>
		</c:when>
		<c:otherwise>
			<p>안녕하세요</p>
		</c:otherwise>
	</c:choose>
	
	
	<h2>반복처리</h2>
	<c:forEach var="i" begin="1" end="6" step="1">
		<%-- <h${i}>Helloworld${i}</h${i}> --%>
		<h${7 - i}>Helloworld${i}</h${7 - i}>
	</c:forEach>
	
	<h3>names</h3>
	<ul>
		<%--
			items 반복접근할 객체 (el)
			var 요소하나를 담을 변수명 (문자열)
			사용 ${var}
		 --%>
		<c:forEach items="${names}" var="name" varStatus="vs">
			<li>${vs.index} ${vs.count} ${vs.first} ${vs.last} ${name}</li>
		</c:forEach>
	</ul>
	
	<h3>items</h3>
	<ul>
		<c:forEach items="${items}" var="item">
			<li>${item.no} ${item.name} ${item.price}</li>
		</c:forEach>
	</ul>
	
	<table>
		<thead>
			<tr>
				<th>No</th>
				<th>이름</th>
				<th>가격</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items}" var="item">			
				<tr>
					<td>${item.no}</td>
					<td>${item.name}</td>
					<td>${item.price}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h3>map</h3>
	<ul>
		<c:forEach items="${map}" var="entry">
			<li>${entry.key} ${entry.value}</li>
		</c:forEach>
	</ul>
	
	<table>
		<tbody>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" value="${map.name}"/>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>
					<input type="number" value="${map.age}"/>
				</td>
			</tr>
			<tr>
				<th>결혼여부</th>
				<td>
					<input type="checkbox" ${map.married ? 'checked' : ''}/>
				</td>
			</tr>
		</tbody>
	</table>
	
	<h2>url</h2>
	<img alt="" src="${pageContext.request.contextPath}/images/file.png">
	<img alt="" src='<c:url value="/images/file.png"/>'/>



	<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
</body>
</html>