<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery - csv</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
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
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
</head>
<body>
	<h1>jQuery - csv</h1>
	<button id="btn1">셀럽 조회</button>
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
	btn1.onclick = () => {
		$.ajax({
			url : "<%= request.getContextPath() %>/jquery/csv",
			success (responseData) {
				console.log(responseData);
				
				let html = "";
				// \n 쪼개기
				responseData.split("\n").forEach((temp) => {
					// , 쪼개기 
					const celeb = temp.split(",");
					console.log(celeb);
					
					html += `
						<tr>
							<td>\${celeb[0]}</td>
							<td>
								<img src="<%= request.getContextPath() %>/images/\${celeb[2]}" />
							</td>
							<td>\${celeb[1]}</td>
							<td>\${celeb[3]}</td>
						</tr>
					`;
				});
				
				const wrapper = document.querySelector(".wrapper");
				const tbody = wrapper.querySelector("tbody");
				tbody.innerHTML = html;
				wrapper.style.display = "block";
			}
		});
	};
	</script>
	<br>
	<br>
	<br>
	<div class="ui-widget">
	  <label for="classmate">우리반 친구들 : </label>
	  <input id="classmate">
	</div>
	<script>
    $("#classmate").autocomplete({
      /**
       * 사용자입력값을 받아, 서버에 ajax요청하고, 결과를 jquery-ui쪽으로 값을 반환.
       */ 
      source (request, response) {
    	  console.log(request);
    	  // console.log(response);
    	  
    	  const {term} = request;
    	  
    	  $.ajax({
    		  url : "<%= request.getContextPath() %>/jquery/csv/autocomplete",
    		  method : "GET",
    		  dataType : "text", // csv타입 없음
    		  data : {
    			  term
    		  },
    		  // data : request,
    		  success(classmates) {
    			  if(classmates === '') return;
    			  
    			  console.log(classmates);
    			  // 김창환$신종환 -> [{label: '김창환', value: '김창환'}, {label: '신종환', value: '신종환'}]
    			  const temp = classmates.split("$");
    			  const arr = temp.map((name) => ({
    				  label : name, // 노출될 값
    				  value : name 	// 내부적으로 처리될 값
    			  }));
    			  console.log(arr);
    			  response(arr); // jquery-ui에 전달
    		  }
    	  });
    	  
      },
      select(event, selected) {
    	  console.log(event, selected);
    	  const {item : {value}} = selected;
    	  // location.href = "/classmate/info?name=" + value;
      }
      
    });
	</script>
	
	
</body>
</html>