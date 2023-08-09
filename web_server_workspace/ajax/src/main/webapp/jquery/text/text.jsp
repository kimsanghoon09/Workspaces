<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery - text</title>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.0.js"></script>
</head>
<body>
	<h1>jQuery - text</h1>
	<button id="btn1">GET</button>
	<button id="btn2">POST</button>
	<fieldset>
		<legend>회원가입</legend>
		<input type="text" name="memberId" id="memberId"/>
		<input type="password" name="password" id="password"/>
	</fieldset>
	<script>
	btn2.onclick = () => {
		const memberId = document.querySelector("#memberId");
		const password = document.querySelector("#password");
		
		// 유효성검사 
		if(!/^.{4,}$/.test(memberId.value)) {
			alert("유효한 아이디를 입력하세요.");
			return;
		}
		if(!/^.{4,}$/.test(password.value)) {
			alert("유효한 비밀번호를 입력하세요.");
			return;
		}
		
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/text",
			method : "POST",
			data : {
				memberId : memberId.value,
				password : password.value
			},
			success(responseText) {
				// alert(responseText);
				document.body.innerHTML += `<h2>\${responseText}</h2>`;
				setTimeout(() => {
					location.reload();
				}, 3000);
				
			}
		});
	}
	
	btn1.onclick = () => {
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/text",
			data : {
				name : "홍길동",
				age : 33
			},
			method : "GET",
			dataType : "text", // 응답받은 자료형
			beforeSend() {
				console.log("beforeSend");
			},
			success (responseText) {
				console.log("success");
				console.log(responseText);
				document.body.append(responseText);
			},
			error () {
				console.log("error");
			},
			complete () {
				console.log("complete");
			}
		});
	};
	
	</script>
	
</body>
</html>