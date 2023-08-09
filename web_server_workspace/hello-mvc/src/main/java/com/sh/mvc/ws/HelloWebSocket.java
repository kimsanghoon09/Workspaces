package com.sh.mvc.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint(value = "/helloWebSocket", configurator = HelloWebSocketConfigurator.class)
public class HelloWebSocket {
	
	// WebSocket 세션을 관리할 맵(멀티스레드에 사용하므로 동기화처리 필수)
	// memberId:String = websocketSession:Session
	public static Map<String, Session> clientMap = 
			Collections.synchronizedMap(new HashMap<>());
	
	// chatroomId:String = Set<String> 해당 chatroom에 접속한 사용자 memberId Set
	public static Map<String, Set<String>> chatroomClientMap = 
			Collections.synchronizedMap(new HashMap<>());
	
	private static void log() {
		System.out.println("[현재 접속자수 : " + clientMap.size() + "명] " + clientMap);
	}
	
	@OnOpen
	public void onOpen(EndpointConfig config, Session session) {
		System.out.println("open");
		Map<String, Object> configProperties = config.getUserProperties();
		String memberId = (String) configProperties.get("memberId");
		
		// 1. clientMap에 저장
		clientMap.put(memberId, session);
		// 2. WebSocket Session객체 properties 맵 객체에 memberId 저장 (@OnClose에서 사용)
		Map<String, Object> sessionProperties = session.getUserProperties();
		sessionProperties.put("memberId", memberId); 
		
		// 채팅방 접속시 사용자 관리
		String chatroomId = (String) configProperties.get("chatroomId");
		if(chatroomId != null) {
			addToChatroom(chatroomId, memberId);			
		}
		
		log();
	}
	

	/**
	 * 특정 chatroom에 사용자추가
	 * @param chatroomId
	 * @param memberId
	 */
	private void addToChatroom(String chatroomId, String memberId) {
		Set<String> chatroomClients = chatroomClientMap.get(chatroomId);
		if(chatroomClients == null) {
			chatroomClients = new HashSet<>();
			chatroomClientMap.put(chatroomId, chatroomClients);
		}
		chatroomClients.add(memberId);
		chatroomLog();
	}
	
	private void removeFromChatroom(String chatroomId, String memberId) {
		Set<String> chatroomClients = chatroomClientMap.get(chatroomId);
		chatroomClients.remove(memberId);
		if(chatroomClients.isEmpty()) {
			chatroomClientMap.remove(chatroomId);
		}
		chatroomLog();
	}

	private void chatroomLog() {
		System.out.print("[현재 채팅방 현황 : ");
		for(String chatroomId : chatroomClientMap.keySet()) {
			System.out.print(chatroomId + "=" + chatroomClientMap.get(chatroomId) + " ");
		}
		System.out.println("]");
		
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		// db chatService.insertChat(chat) 시작!
		
		System.out.println("message : " + message);
		Map<String, Object> payload = new Gson().fromJson(message, Map.class);
		System.out.println("payload from message : " + payload);
		String chatroomId = (String) payload.get("chatroomId");
		try {
			// 같은 채팅방 사용자에게 전송
			Set<String> chatroomClients = chatroomClientMap.get(chatroomId);
			for(String memberId : chatroomClients) {
				Session wsSession = clientMap.get(memberId);
				Basic basic = wsSession.getBasicRemote();
				basic.sendText(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@OnError
	public void onError(Throwable e) {
		System.out.println("error");
		e.printStackTrace();
	}
	@OnClose
	public void onClose(Session session) {
		System.out.println("close");
		Map<String, Object> sessionProperties = session.getUserProperties();
		String memberId = (String) sessionProperties.get("memberId");
		clientMap.remove(memberId); // 해당 memberId의 WebSocket Session객체 제거
		log();
		
		String chatroomId = (String) sessionProperties.get("chatroomId");
		if(chatroomId != null) {
			removeFromChatroom(chatroomId, memberId);
		}
		
		
	}

	
}
