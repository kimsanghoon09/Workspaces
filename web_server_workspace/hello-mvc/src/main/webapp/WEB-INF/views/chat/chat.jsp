<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>  
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/chat.css" />
	<section id="chat-container">	
		<h2>채팅</h2>
		<div id="msg-container">
			<ul></ul>
		</div>
		
		<div id="msg-editor" class="editor">
			<form name="chatMsgFrm">
				<textarea name="msg" id="msg" cols="30" rows="2" pattern="(.|\n)+"
					required></textarea>
				<button type="submit" id="send">Send</button>
			</form>
		</div>
	</section>
	<script>
	document.chatMsgFrm.onsubmit = (e) => {
		e.preventDefault(); // 동기적 폼제출 방지
		const payload = {
			messageType : "CHAT_MESSAGE",
			chatroomId : "<%= session.getAttribute("chatroomId") %>",
			sender : "<%= loginMember.getMemberId() %>",
			message : e.target.msg.value,
			createdAt : Date.now()
		};
		ws.send(JSON.stringify(payload));
		e.target.reset();
		e.target.msg.focus();
	};
	</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>  
