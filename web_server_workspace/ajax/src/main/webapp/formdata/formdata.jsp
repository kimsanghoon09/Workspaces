<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FormData</title>
    <meta charset="utf-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha512-3gJwYpMe3QewGELv8k/BX9vcqhryRdzRMxVfq6ngyWXwo03GFEzjsUm8Q7RZcHPHksttq7/GFoxjCVUjkjvPdw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
    <h1>FormData</h1>
    <form name="frm">
        <input type="text" name="name" id="name" placeholder="이름 입력" value="홍길동">
        <br>
        <input type="file" name="profile" id="profile">
    </form>
    <fieldset>
        <legend>jQuery</legend>
        <button id="btn1">GET</button>
        <button id="btn2">POST</button>
    </fieldset>
    <fieldset>
        <legend>fetch</legend>
        <button id="btn3">GET</button>
        <button id="btn4">POST</button>
    </fieldset>
    <script>
    /**
     * FormData객체를 GET방식으로 보낼수 없다.
     * 다만, URLSearchParams객체를 통해 queryString으로 쉽게 변환할 수 있다.
     */
    btn1.onclick = () => {
        const frmData = new FormData(document.frm);
        for(const name of frmData.keys())
            console.log(`\${name}=\${frmData.get(name)}`);

        const queryString = new URLSearchParams(frmData).toString();
        $.ajax({
            url : "<%= request.getContextPath() %>/formdata",
            method : "GET",
            data : queryString,
            success(responseData){
                console.log(responseData);
            }
        });
    }

    /**
     * FormDat $.ajax POST 전송시 processData, contentType 설정 필수
     */
    btn2.onclick = () => {
        const frmData = new FormData(document.frm);
        for(const name of frmData.keys())
            console.log(`\${name}=\${frmData.get(name)}`);

        $.ajax({
            url : "<%= request.getContextPath() %>/formdata",
            method : "POST",
            data : frmData,
            processData : false,
            contentType : false,
            success(responseData){
                console.log(responseData);
            }
        });
    }

    btn3.onclick = () => {
        const frmData = new FormData(document.frm);
        fetch(`<%= request.getContextPath() %>/formdata?\${new URLSearchParams(frmData).toString()}`)
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
        });
    }

    /**
     * FormData 객체 전송시 요청헤더 자동으로 설정
     * - Content-Type: multipart/form-data; boundary=----WebKitFormBoundarytA2yuwHAzCjkI6AY
     */
    btn4.onclick = () => {
        const frmData = new FormData(document.frm);
        fetch('<%= request.getContextPath() %>/formdata', {
            method: 'POST',
            cache: 'no-cache',
            body: frmData // body 부분에 폼데이터 변수를 할당
        })
        .then((response) => response.text())
        .then((data) => {
            console.log(data);
        });
    }
    </script>


</body>
</html>
