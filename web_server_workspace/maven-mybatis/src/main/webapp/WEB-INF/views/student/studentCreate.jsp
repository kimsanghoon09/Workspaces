<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 등록</title>
<style>
div#student-container{text-align:center;}
table.tbl-student{margin:0 auto;border:1px solid; border-collapse:collapse;}
table.tbl-student th,table.tbl-student td{
    border:1px solid;
    padding:5px;
}
</style>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.0.js"></script>
</head>
<body>
    <h1>학생 등록</h1>
    <div id="student-container">
        <form 
        	name="studentCreateFrm" 
        	method="POST" 
        	action="${pageContext.request.contextPath}/student/studentCreate.do">
            <table class="tbl-student">
                <tbody>
                    <tr>
                        <th>이름</th>
                        <td>
                            <input type="text" name="name" required/>
                        </td>
                    </tr>
                    <tr>
                        <th>전화번호</th>
                        <td>
                            <input type="tel" name="tel" maxlength="11" required/>
                        </td>
                    </tr>
                    <tr>
                    	<td colspan="2">
                    		<input type="submit" value="등록"/>
                    		<input type="button" onclick="asyncCreateStudent();" value="비동기 등록"/>
                    	</td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
	<script>
	const asyncCreateStudent = () => {
		const frm = document.studentCreateFrm;
		const name = frm.name.value;
		const tel = frm.tel.value;
		$.ajax({
			url : '${pageContext.request.contextPath}/student/studentCreate2.do',
			data : {
				name, 
				tel
			},
			method : "POST",
			success(responseData) {
				console.log(responseData);
				const {student : {id, name, tel, createdAt}} = responseData;
				alert(`학생등록 성공!
-------------------------
ID : \${id}
이름 : \${name}
전화번호 : \${tel}
등록일 : \${createdAt}
-------------------------`);
				location.href = "${pageContext.request.contextPath}/student/studentList.do";
			}
		});
	};
	
	</script>
</body>
</html>