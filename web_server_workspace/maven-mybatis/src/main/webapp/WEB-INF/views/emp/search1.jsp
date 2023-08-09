<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mybatis실습</title>
<style>
div#emp-container{text-align:center;}
table.tbl-emp{
	margin:0 auto;
	border:1px solid; 
	border-collapse:collapse;
}
table.tbl-emp th, table.tbl-emp td{
	border:1px solid;
	padding:5px;
}
div#search-container{
	padding:15px 0;
}
</style>
</head>
<body>
<div id="emp-container">
	<h2>사원정보 </h2>
	<div id="search-container">
		<form action="" >
			<select name="searchType" required>
				<option value="" disabled selected>검색타입</option>
				<!-- 최초보이는 값으로 설정함. required이기 때문에 반드시 다른값을 선택해야함. value="" 반드시 있어야함.-->
				<option value="emp_name" ${param.searchType eq 'emp_name' ? 'selected' : ''}>사원명</option>
				<option value="email" ${param.searchType eq 'email' ? 'selected' : ''}>이메일</option>
				<option value="phone" ${param.searchType eq 'phone' ? 'selected' : ''}>전화번호</option>
			</select>
			<input type="search" name="searchKeyword" value="${param.searchKeyword}" required/>	
			<input type="submit" value="검색" />
		</form>
	</div>	
	<table class="tbl-emp">
		<thead>
			<tr>
				<th></th>
				<th>사번</th>
				<th>사원명</th>
				<th>주민번호</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>부서코드</th>
				<th>직급코드</th>
				<th>급여레벨</th>
				<th>급여</th>
				<th>보너스율</th>
				<th>매니져 사번</th>
				<th>입사일</th>
			</tr>
		</thead>
		<tbody>
			<%-- 검색결과가 없을때 --%>
			<c:if test="${empty emps}">
				<tr>
					<td colspan="13">검색 결과가 없습니다.</td>
				</tr>
			</c:if>
			<%-- 검색결과가 있을때 --%>
			<c:if test="${not empty emps}">
				<c:forEach items="${emps}" var="emp" varStatus="vs">				
					<tr>
						<td>${vs.count}</td>				
						<td>${emp.empId}</td>				
						<td>${emp.empName}</td>				
						<td>${fn:substring(emp.empNo, 0, 8)}******</td>				
						<td>${emp.email}</td>				
						<td>${emp.phone}</td>				
						<td>${emp.deptCode}</td>				
						<td>${emp.jobCode}</td>				
						<td>${emp.salLevel}</td>				
						<td>
							<fmt:formatNumber value="${emp.salary}" type="currency"/>
						</td>				
						<td>
							<fmt:formatNumber value="${emp.bonus}" type="percent"/>
						</td>				
						<td>${emp.managerId}</td>				
						<td>
							<fmt:parseDate value="${emp.hireDate}" pattern="yyyy-MM-dd" var="hireDate"/>
							<fmt:formatDate value="${hireDate}" pattern="yyyy/MM/dd"/>
						</td>				
					</tr>
				</c:forEach>
			</c:if>
		
		</tbody>
	</table>
</div>

</body>
</html>