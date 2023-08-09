package com.sh.app.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.sh.app.member.service.MemberService;
import com.sh.app.ws.dto.Payload;
import com.sun.nio.sctp.Notification;

import lombok.extern.slf4j.Slf4j;

/**
 * ApplicationDestinationPrefix로 시작하는 ws 요청은 이 핸들러를 거친다.
 *  
 *
 */
@Controller
@Slf4j
public class StompMessageController {
	
//	@Autowired
//	private NotificationService notificationSErvice;
	
	/**
	 * prefix를 제외한 url만 작성
	 * 
	 * @return
	 */
	@MessageMapping("/notice")
	@SendTo("/app/notice")
	public Payload notice(Payload message) {
		log.debug("message = {}", message);
		return message;
	}
	
	 
	@MessageMapping("/notice/{memberId}")
	@SendTo("/app/notice/{memberId}")
	public Payload noticeEach(@DestinationVariable String  memberId, Payload message) {
		log.debug("memberId = {}", memberId);
		log.debug("message = {}", message);
		return message;
	}
}
