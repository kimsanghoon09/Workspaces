package com.sh.mvc.ws;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import com.sh.mvc.member.model.vo.Member;

public class HelloWebSocketConfigurator extends Configurator {
	
	/**
	 * 사용자연결을 위한 WebSocket용 Session객체 생성시 메소드를 호출
	 * - HttpSession에 등록된 loginMember객체의 memberId를 가져와서
	 * - SeverEndpointConfig객체의 properties 맵 객체에 저장,
	 * - Endpoint클래스의 @OnOpen메소드에서 사용할수 있다.
	 */
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		System.out.println("HelloWebSocketConfigurator#modifyHandshake 실행");
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		
		// memberId 관리
		Member loginMember = (Member) httpSession.getAttribute("loginMember");
		String memberId = loginMember.getMemberId();
		
		Map<String, Object> configProperties = sec.getUserProperties();
		configProperties.put("memberId", memberId);
		
		// /chat/chat 페이지에서 ws:// 요청하는 경우 
		// 채팅방 접속시 chatroomId 관리
		String chatroomId = (String) httpSession.getAttribute("chatroomId");
		if(chatroomId != null) {
			configProperties.put("chatroomId", chatroomId);
			// 채팅방을 벗어나는 경우
			httpSession.removeAttribute("chatroomId");
		}
		
		
	}
}





