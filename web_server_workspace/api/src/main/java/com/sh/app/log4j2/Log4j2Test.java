package com.sh.app.log4j2;

import lombok.extern.log4j.Log4j2;

/**
 * Log4j2
 * - 프로젝트 콘솔출력/로그파일등의 기록을 관리하는 프레임워크
 * - 설정한 레벨에 따라 제한적인 로깅이 가능
 * 
 *  FATAL
 *  ERROR
 *  WARN
 *  INFO
 *  DEBUG
 *  TRACE
 *  
 *
 */
@Log4j2
public class Log4j2Test {
	// private static final Logger log = LogManager.getLogger(Log4j2Test.class);
	
	public static void main(String[] args) {
		log.fatal("치명적인 오류!!!");
		log.error("예외발생!!!");
		log.warn("경고 - 현재 실행에는 문제없지만, 잠재적인 오류 경고");
		log.info("정보 - 서비스 운영중에 필요한 정보제공");
		
		int i = 10;
		log.debug("개발에 필요한 출력 i = {}", i);
		log.trace("코드흐름 파악");
	}

}
