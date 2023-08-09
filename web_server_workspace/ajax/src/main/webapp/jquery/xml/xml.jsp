<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQUery - xml</title>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.0.js"></script>
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
</head>
<body>
	<h1>jQUery - xml</h1>
	<button id="btn1">sample.xml</button>
	<div class="books-wrapper">
		<table>
			<thead>
				<tr>
					<th>제목</th>
					<th>카테고리</th>
					<th>저자</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<script>
	btn1.onclick = () => {
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/xml/sample.xml",
			method : "GET",
			dataType : "xml",
			success(xmlDoc) {
				console.log(xmlDoc); // (jquery가 선작업) document xml문서를 파싱해서 DOM Tree 결과물
				console.dir(xmlDoc);
				
				const tbody = document.querySelector(".books-wrapper table tbody");
				tbody.innerHTML = '';
				
				const root = xmlDoc.querySelector(":root");
				console.log("root = ", root);
				console.log(root.getAttribute("myattr")); // 태그속성 가져오기
				
				const books = [...root.children];
				console.log("books = ", books);
				
				books.forEach((book) => {
					const [subject, title, author] = book.children;
					console.log(subject, title, author);
					
					tbody.innerHTML += `
						<tr>
							<td>\${title.textContent}</td>
							<td>\${subject.textContent}</td>
							<td>\${author.textContent}</td>
						</tr>
					`;
					
				});
			}
			
		});
	};
	</script>
	
	<button id="btn2">셀럽 조회</button>
	<div class="wrapper" style="display: none;">
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
	btn2.onclick = () => {
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/xml",
			method : "GET",
			dataType : "xml",
			success(xmlDoc) {
				console.log(xmlDoc);
				const wrapper = document.querySelector(".wrapper");
				const tbody = wrapper.querySelector("table tbody");
				tbody.innerHTML = '';
				
				const root = xmlDoc.querySelector(":root"); // <celebs>...</celebs>
				const celebs = [...root.children]; // Array.from()
				console.log("celebs = ", celebs);
				
				celebs.forEach((celeb) => {
					const [no, name, profile, celebType] = celeb.children;
					
					tbody.innerHTML += `
						<tr>
							<td>\${no.textContent}</td>
							<td>
								<img src="<%= request.getContextPath() %>/images/\${profile.textContent}" />
							</td>
							<td>\${name.textContent}</td>
							<td>\${celebType.textContent}</td>
						</tr>
					`;
					
				});
				wrapper.style.display = "block";
				
			}
		});
		
	};
	</script>
	
	<br/>
	<br/>
	<fieldset>
		<input type="date" id="targetDt"/>		
		<button id="btn3">영화 박스오피스</button>
		<div class="movie-wrapper">
			<table>
				<thead>
					<tr>
						<th>순위</th>
						<th>제목</th>
						<th>누적관객수(만)</th>					
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</fieldset>
	<script>
	window.onload = () => {
		const f = (n) => n < 10 ? "0" + n : n;
		// 1. #targetDt value 어제 날짜로 세팅
		const today = new Date();
		const yesterday = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 1);
		document.querySelector("#targetDt").value = 
			`\${yesterday.getFullYear()}-\${f(yesterday.getMonth() + 1)}-\${f(yesterday.getDate())}`;
		// 2. #targetDt value을 불러와서 박스오피스 요청
		getDailyBoxOffice();
	};
	
	btn3.onclick = () => {
		getDailyBoxOffice();
	};
	
	const getDailyBoxOffice = () => {
		const targetDt = document.querySelector("#targetDt");
		const targetDtVal = targetDt.value.replaceAll("-", ""); // 1990-09-09 -> 19900909
		
		$.ajax({
			url : "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml",
			data : {
				key : "fa2ce7308ff7ef70205103ecc11b5d9c",
				targetDt : targetDtVal
			},
			success(xmlDoc) {
				console.log(xmlDoc);
				
				const tbody = document.querySelector(".movie-wrapper table tbody");
				tbody.innerHTML = '';
				
				const root = xmlDoc.querySelector(":root");
				const [,,dailyBoxOfficeList] = root.children;
				console.log(dailyBoxOfficeList);
				[...dailyBoxOfficeList.children].forEach((dailyBoxOffice) => {
					const rank = dailyBoxOffice.querySelector("rank");
					const movieNm = dailyBoxOffice.querySelector("movieNm");
					const audiAcc = dailyBoxOffice.querySelector("audiAcc");
					
					
					const audiAccVal = audiAcc.textContent > 10000 ? 
											Math.floor(audiAcc.textContent / 10000) + "만" : 
												audiAcc.textContent;
					tbody.innerHTML += `
						<tr>
							<td>\${rank.textContent}</td>
							<td>\${movieNm.textContent}</td>
							<td>\${audiAccVal}</td>
						</tr>
					`;
					
				});
				
			}
		});
	};
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
	<br/>
	<br/>
</body>
</html>