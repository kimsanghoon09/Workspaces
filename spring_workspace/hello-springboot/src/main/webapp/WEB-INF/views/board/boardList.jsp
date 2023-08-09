<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
</style>
<section id="board-container" class="container">
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success"/>
	<table id="tbl-board" class="table table-striped table-hover">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>첨부파일</th> <!-- 첨부파일 있을 경우, /resources/images/file.png 표시 width: 16px-->
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty boards}">
				<tr>
					<td colspan="5" class="text-center">조회된 게시글이 없습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty boards}">
				<c:forEach items="${boards}" var="board" varStatus="vs">
					<tr>
						<td>${board.id}</td>
						<td>
							<a href="${pageContext.request.contextPath}/board/boardDetail.do?id=${board.id}">${board.title}</a>
						</td>
						<td>${board.memberId}</td>
						<td>
							<c:if test="${board.attachCount gt 0}">
								<img alt="" src="${pageContext.request.contextPath}/resources/images/file.png" width="16px">
							</c:if>
						</td>
						<td>
							<fmt:parseDate value="${board.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createdAt"/>
							<fmt:formatDate value="${createdAt}" pattern="yy/MM/dd HH:mm"/>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</section> 
<script>
document.querySelector("#btn-add").onclick = () => {
	location.href = '${pageContext.request.contextPath}/board/boardCreate.do';
};
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
