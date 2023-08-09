<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery - html</title>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.0.js"></script>
<style>
table {
	border: 1px solid #000;
	border-collapse: collapse;
}
th, td {
	border: 1px solid #000;
	padding: 5px;
}
table img {
	width: 150px;
}

</style>
</head>
<body>
	<h1>jQuery - html</h1>
	<button id="btn1">셀럽 조회</button>
	<div class="wrapper"></div>
	<script>
	btn1.onclick = () => {
		
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/html",
			// method : "GET",
			// dataType : "html",
			success (responseHtml) {
				console.log(responseHtml);
				document.querySelector(".wrapper").innerHTML = responseHtml;
			}
		});
		
	};
	</script>




</body>
</html>