<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 목록</title>
<style>
div#student-container{text-align:center;}
table.tbl-student{margin:0 auto;border:1px solid; border-collapse:collapse;}
table.tbl-student th,table.tbl-student td{
	border:1px solid;
	padding:5px;
}
.btn-wrapper {
	display: flex;
	margin: 20px;
    justify-content: space-around;
}

</style>
</head>
<body>
	<h1>학생 목록</h1>
	<div class="student-container">
	    <table class="tbl-student">
	    	<thead>
		        <tr>
		            <th>학번</th>
		            <th>이름</th>
		            <th>전화번호</th>
		            <th>등록일</th>
		        </tr>
	    	</thead>
	    	<tbody>
	    		<c:if test="${empty students}">
	    			<tr>
	    				<td colspan="4">조회된 학생이 없습니다.</td>
	    			</tr>
	    		</c:if>
	    		<c:if test="${not empty students}">
	    			<c:forEach items="${students}" var="student">
	    				<tr>
	    					<td>
	    						<a href="${pageContext.request.contextPath}/student/studentDetail.do?id=${student.id}">${student.id}</a>
	    					</td>
	    					<td>${student.name}</td>
	    					<td>${student.tel}</td>
	    					<td>
	    						<fmt:parseDate value="${student.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt"/>
	    						<fmt:formatDate value="${createdAt}" pattern="yy/MM/dd HH:mm:ss"/>
	    					</td>
	    				</tr>
	    			</c:forEach>
	    		</c:if>
	    	</tbody>
		</table>
		<c:set var="page" value="${empty param.page ? 1 : param.page}"/>
		<div class="btn-wrapper">
			<button id="prev" ${page eq 1 ? 'disabled' : ''}>이전</button>
			<button id="next" ${page eq lastPage ? 'disabled' : ''}>다음</button>
		</div>
	</div>
	<script>
	const page = ${page};
	document.querySelector("#prev").onclick = () => {
		location.href = `${pageContext.request.contextPath}/student/studentList.do?page=\${page - 1}`;
	}	
	document.querySelector("#next").onclick = () => {
		location.href = `${pageContext.request.contextPath}/student/studentList.do?page=\${page + 1}`;
	}
	
	</script>
</body>
</html>