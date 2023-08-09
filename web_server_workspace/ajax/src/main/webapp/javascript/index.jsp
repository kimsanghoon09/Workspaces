<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Javascript Ajax</title>
</head>
<body>
	<h1>Javascript Ajax</h1>
	<button id="btn1">GET</button>
	<button id="btn2">POST</button>
	<script>
	let xhr;
	/**
	 * 1. XMLHttpRequest 객체 생성
	 * 2. 응답처리 onreadystatechange 핸들러 
	 * 3. 요청 open - send
	 */
	btn2.onclick = () => {
		xhr = getXMLHttpRequest();
		xhr.onreadystatechange = (e) => {
			if (xhr.readyState === 4 && xhr.status === 200) {
				console.log(xhr.responseText);	
			}
		};
		
		xhr.open("POST", "<%= request.getContextPath() %>/javascript/test");
		
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send("name=신사임당&age=48");
	};
	 
	 
	btn1.onclick = () => {
		xhr = getXMLHttpRequest();
		// console.log(xhr);
		
		xhr.onreadystatechange = (e) => {
			// console.log(xhr.readyState, e);
			switch(xhr.readyState) {
			case 0: console.log("uninitialized"); break;
			case 1: console.log("loading"); break; // open호출
			case 2: console.log("loaded"); break; // send호출
			case 3: console.log("interactive"); break; // 응답 일부 도착
			case 4: console.log("completed"); // 모든 응답 완료
				console.log(xhr.status); 
				if(xhr.status === 200) {
					const body = xhr.responseText;
					alert(body);
				}
			}
			
		};
		
		xhr.open("GET", "<%= request.getContextPath() %>/javascript/test?name=홍길동&age=33");
		xhr.send();
		
	}
	
	// const getXMLHttpRequest = () => new XMLHttpRequest();
	function getXMLHttpRequest(){
        // IE일 경우
        if(window.ActiveXObject){
            try{ // IE 9버전 이상일 경우
                return new ActiveXObject("Msxml2.XMLHTTP");
            } catch (ex) {
                // IE 8버전 이하일 경우
                try {
                    return new ActiveXObject("Microsoft.XMLHTTP");
                } catch (ex) {
                    return null;
                }
            }
        } 
        // IE이외의 브라우저일 경우
        else if(window.XMLHttpRequest) {
            return new XMLHttpRequest(); 
        } 
        else {
            return null;
        }
    };
	</script>
	

</body>
</html>