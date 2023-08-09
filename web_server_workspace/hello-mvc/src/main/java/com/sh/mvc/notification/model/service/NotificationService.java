package com.sh.mvc.notification.model.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import com.google.gson.Gson;
import com.sh.mvc.board.model.vo.Board;
import com.sh.mvc.ws.HelloWebSocket;
import com.sh.mvc.ws.MessageType;

/**
 * 
 * 알림 요청이 있을때마다 
 * - 1. db notification테이블 저장 (생략)
 * - 2. HelloWebSocket.clientMap에서 해당 사용자를 찾아서 실시간 알림처리
 *
 * honggd 게시글 작성 - sinsa 해당게시글에 댓글 작성 - honggd 게시글댓글 알림 
 */
public class NotificationService {

	/**
	 * 1. db저장
	 * 2. 실시간알림
	 * 
	 * @param board
	 * @return
	 */
	public int notifyNewBoardComment(Board board) {
		// 1. 저장
//		int result = notificationDao.insertNotification(conn, notification);
		// no, messageType, receiver, createdAt, message, checked
		
		// 2.실시간 알림
		// WebSocket Session 가져오기
		Session wsSession = HelloWebSocket.clientMap.get(board.getWriter());
		if(wsSession != null) {
			Basic basic = wsSession.getBasicRemote();
			try {
				Map<String, Object> payload = new HashMap<>();
				payload.put("messageType", MessageType.NEW_BOARD_COMMENT);
				payload.put("receiver", board.getWriter());
				payload.put("createdAt", System.currentTimeMillis());
				payload.put("message", board.getTitle() + "(" + board.getNo() + ") 게시글에 댓글이 달렸습니다.");
				basic.sendText(new Gson().toJson(payload));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}


