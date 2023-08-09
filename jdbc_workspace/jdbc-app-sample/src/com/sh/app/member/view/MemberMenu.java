package com.sh.app.member.view;

import com.sh.app.member.controller.MemberController;
import com.sh.app.member.model.dto.Member;

public class MemberMenu {
	
	private MemberController memberController = new MemberController();
	/**
	 * 회원가입
	 */
	public void registerMember() {
		
	}

	/**
	 * 로그인
	 * - 입력한 id/password와 일치하는 회원이 있다면 로그인 성공
	 * @return 
	 */
	public Member login() {
		System.out.println("> 로그인 성공했습니다. 😃");
		return new Member();
	}

}
