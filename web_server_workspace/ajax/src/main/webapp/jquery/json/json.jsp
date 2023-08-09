<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery - json</title>
<style>
table {
	margin: 20px 0;
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
<script src="<%= request.getContextPath() %>/js/jquery-3.7.0.js"></script>
</head>
<body onload="findAllCeleb();">
	<h1>jQuery - json</h1>
	<button id="btn1">전체조회</button>
	<div class="wrapper">
		<table>
			<thead>
				<tr>
					<th>No</th>
					<th>프로필</th>
					<th>이름</th>
					<th>타입</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<script>
	btn1.onclick = () => {
		findAllCeleb();
	}
	const findAllCeleb = () => {
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/json/celeb/findAll",
			dataType : "json",
			success(celebs) {
				console.log(celebs); // json 문자열 -> js object 파싱처리
				
				const tbody = document.querySelector(".wrapper table tbody");
				tbody.innerHTML = celebs.reduce((html, celeb) => {
					const {no, name, profile, celebType} = celeb;
					return html + `
						<tr>
							<td>\${no}</td>
							<td>
								<img src="<%= request.getContextPath() %>/images/\${profile}"/>
							</td>
							<td><a href="javascript:findOneCeleb(\${no});">\${name}</a></td>
							<td>\${celebType}</td>
						</tr>
					`;
				}, "");
				
			}
		});
	}
	</script>
	
	<form name="celebEnrollFrm">
		<fieldset>
			<legend>Celeb 등록폼</legend>
			<table>
				<tbody>
					<tr>
						<th>
							<label for="celeb-enroll-name">Name</label>
						</th>
						<td>
							<input type="text" name="name" id="celeb-enroll-name" />
						</td>
					</tr>
					<tr>
						<th>
							<label for="celeb-enroll-type">Type</label>
						</th>
						<td>
							<select name="type" id="celeb-enroll-type">
								<option value="ACTOR">ACTOR</option>
								<option value="SINGER">SINGER</option>
								<option value="MODEL">MODEL</option>
								<option value="COMEDIAN">COMEDIAN</option>
								<option value="ENTERTAINER">ENTERTAINER</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<label for="celeb-enroll-profile">Profile</label>
						</th>
						<td>
							<input type="file" name="profile" id="celeb-enroll-profile" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button>등록</button>
						</td>
					</tr>
				</tbody>
			</table>		
		</fieldset>
	</form>
	<script>
	/** 
	 * 동기식 POST요청 (DML처리) - redirect(URL변경)
	 * 비동기식 POST요청 (DML처리) - redirect 없음. 결과 map / 생성된 객체등 반환 
	 */
	document.celebEnrollFrm.onsubmit = (e) => {
		// 파일업로드를 포함한 비동기 요청
		// 1. FormData객체 사용
		// 2. ajax 속성 - processData: false, contentType: false
		const frmData = new FormData(e.target);
		
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/json/celeb/enroll",
			data : frmData,
			processData : false, // 직렬화 처리방지
			contentType : false, // 기본 Content-Type : application/x-www-form-urlencoded 사용안함. 
			method : "POST",
			dataType : "json",
			success(responseData) {
				console.log(responseData);
				const {result, message} = responseData;
				alert(message);
				
				findAllCeleb(); 
			},
			complete() {
				e.target.reset(); // 폼 초기화
			}
			
		});
		
		
		e.preventDefault(); // 동기적 폼제출 방지
	};
	</script>
	
	<br/>
	<br/>
	<br/>
	
	<form name="celebUpdateFrm">
		<fieldset>
			<legend>Celeb 수정폼</legend>
			<table>
				<tbody>
					<tr>
						<th>
							<label for="celeb-update-no">No</label>
						</th>
						<td>
							<input type="text" name="no" id="celeb-update-no" readonly/>
						</td>
					</tr>
					<tr>
						<th>
							<label for="celeb-update-name">Name</label>
						</th>
						<td>
							<input type="text" name="name" id="celeb-update-name" />
						</td>
					</tr>
					<tr>
						<th>
							<label for="celeb-update-type">Type</label>
						</th>
						<td>
							<select name="type" id="celeb-update-type">
								<option value="ACTOR">ACTOR</option>
								<option value="SINGER">SINGER</option>
								<option value="MODEL">MODEL</option>
								<option value="COMEDIAN">COMEDIAN</option>
								<option value="ENTERTAINER">ENTERTAINER</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<label for="celeb-update-profile">Profile</label>
						</th>
						<td>
							<div>
								<img src="" id="celeb-update-profile-viewer"/>
							</div>
							<input type="file" name="profile" id="celeb-update-profile" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button>수정</button>
							<button type="button" id="btn-celeb-delete">삭제</button>
						</td>
					</tr>
				</tbody>
			</table>		
		</fieldset>
	</form>
	<script>
	document.querySelector("#btn-celeb-delete").onclick = () => {
		const no = document.querySelector("#celeb-update-no").value;
		if(!no) return;	
		
		if(confirm("정말 삭제하시겠습니까?")) {
			$.ajax({
				url : "<%= request.getContextPath() %>/jquery/json/celeb/delete",
				data : {no},
				method : "POST",
				success(responseData) {
					console.log(responseData);
					alert(responseData.message);
				},
				complete() {
					document.celebUpdateFrm.reset(); // 폼 초기화
					document.querySelector("#celeb-update-profile-viewer").src = ''; // 이미지 초기화
					findAllCeleb();
				}
			});
		}
	};
	
	const findOneCeleb = (no) => {
		console.log(no);
		
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/json/celeb/findOne",
			data : {no},
			success(celeb) {
				console.log(celeb);
				const {no, name, profile, celebType} = celeb;
				// document.celebUpdateFrm에 데이터 채우기
				const frm = document.celebUpdateFrm;
				frm.no.value = no;
				frm.name.value = name;
				frm.type.value = celebType;
				document.querySelector("#celeb-update-profile-viewer").src = 
					`<%= request.getContextPath() %>/images/\${profile}`;
			}
		});
	};
	
	/**
	 * 폼 동기적 제출방지
	 * 파일업로드를 위한 설정
	 * 1. FormData
	 * 2. $.ajax({processData, contentType})
	 */
	document.celebUpdateFrm.onsubmit = (e) => {
		e.preventDefault();
		
		const frmData = new FormData(e.target);
		
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/json/celeb/update",
			data : frmData,
			processData : false,
			contentType : false,
			method : "POST",
			success(responseData) {
				console.log(responseData);
				alert(responseData.message);
			},
			complete() {
				e.target.reset(); // 폼 초기화
				document.querySelector("#celeb-update-profile-viewer").src = ''; // 이미지 초기화
				findAllCeleb();
			}
		});
		
		
	}
	</script>
	
	
	
	
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	
</body>
</html>