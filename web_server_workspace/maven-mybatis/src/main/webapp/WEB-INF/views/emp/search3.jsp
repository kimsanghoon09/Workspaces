<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String[] _jobCodes = request.getParameterValues("jobCode");
	List<String> jobCodes = null;
	if(_jobCodes != null) {
		jobCodes = Arrays.asList(_jobCodes);
		pageContext.setAttribute("jobCodes", jobCodes);		
	}

%>
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
table#tbl-search {
	margin: 0 auto;
}
</style>
</head>
<body>
<div id="emp-container">
	<h2>사원정보 </h2>
	<div id="search-container">
		<form action="" >
            <p>
                <h3>검색</h3>
                <input type="button" value="초기화" onclick="location.href='${pageContext.request.contextPath}/emp/search3.do';"/>
            </p>
            <table id="tbl-search">
                <tr>
					<th>직급</th>
					<td>
						<input type="checkbox" name="jobCode" id="J1" value="J1" ${jobCodes.contains('J1') ? 'checked' : ''}/>
						<label for="J1">대표</label>
						<input type="checkbox" name="jobCode" id="J2" value="J2" ${jobCodes.contains('J2') ? 'checked' : ''}/>
						<label for="J2">부사장</label>
						<input type="checkbox" name="jobCode" id="J3" value="J3" ${jobCodes.contains('J3') ? 'checked' : ''}/>
						<label for="J3">부장</label>
						<br />
						<input type="checkbox" name="jobCode" id="J4" value="J4" ${jobCodes.contains('J4') ? 'checked' : ''}/>
						<label for="J4">차장</label>
						<input type="checkbox" name="jobCode" id="J5" value="J5" ${jobCodes.contains('J5') ? 'checked' : ''}/>
						<label for="J5">과장</label>
						<input type="checkbox" name="jobCode" id="J6" value="J6" ${jobCodes.contains('J6') ? 'checked' : ''}/>
						<label for="J6">대리</label>
						<br />
						<input type="checkbox" name="jobCode" id="J7" value="J7" ${jobCodes.contains('J7') ? 'checked' : ''}/>
						<label for="J7">사원</label>	
					</td>
				</tr>
                <tr>
                    <th colspan="2">
                        <input type="submit" id="btn-search" value="검색"  />
                    </th>
                </tr>
            </table>
		</form>
	</div>	
	<table class="tbl-emp">
		<thead>
			<tr>
				<th></th>
				<th>사번</th>
				<th>사원명</th>
				<th>주민번호</th>
				<th>성별</th>
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
						<td>${emp.gender}</td>		
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