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
                <input type="button" value="초기화" onclick="location.href='${pageContext.request.contextPath}/emp/search2.do';"/>
            </p>
            <table id="tbl-search">
                <tr>
                    <th>성별</th>
                    <td>
                        <input type="radio" name="gender" value='남' id="gender0" ${param.gender eq '남' ? 'checked' : ''}/>
                        <label for="gender0">남</label>
                        <input type="radio" name="gender" value='여' id="gender1" ${param.gender eq '여' ? 'checked' : ''}/>
                        <label for="gender1">여</label>
                    </td>
                </tr>
                <tr>
					<th>급여</th>
					<td>
						<input type="number" name="salary" min="0" step="500000" value="${param.salary}"/>
						<input type="radio" name="salaryCompare" id="salary_ge" value='ge' ${param.salaryCompare eq 'ge' ? 'checked' : ''}/>
						<label for="salary_ge">이상</label>
						<input type="radio" name="salaryCompare" id="salary_le" value='le' ${param.salaryCompare eq 'le' ? 'checked' : ''}/>
						<label for="salary_le">이하</label>
					</td>
				</tr>
                <tr>
                    <th colspan="2">
                        <input type="submit" id="btn-search" value="검색"  />
                        <input type="reset" value="초기화(Not working)"  />
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