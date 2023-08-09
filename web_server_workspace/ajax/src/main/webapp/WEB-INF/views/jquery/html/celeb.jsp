<%@page import="com.sh.ajax.celeb.model.vo.Celeb"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Celeb> celebs = (List<Celeb>) request.getAttribute("celebs");
%>
<table>
	<thead>
		<tr>
			<th>No</th>
			<th>프로필</th>
			<th>이름</th>
			<th>타입</th>
		</tr>
	</thead>
	<tbody>
		<%	for(Celeb celeb : celebs) {%>
			<tr>
				<td><%= celeb.getNo() %></td>
				<td>
					<img src="<%= request.getContextPath() %>/images/<%= celeb.getProfile() %>"/>
				</td>
				<td><%= celeb.getName() %></td>
				<td><%= celeb.getCelebType() %></td>
			</tr>
		<%  } %>
	</tbody>
</table>