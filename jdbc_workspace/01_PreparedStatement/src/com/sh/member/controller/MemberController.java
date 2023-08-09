package com.sh.member.controller;

import java.sql.Date;
import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;
/**
 * 
 * MVC패턴의 제어센터 - Controller클래스
 * - 모든 요청은 이 controller클래스를 통해야 한다.
 *
 */
public class MemberController {
	
	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {
		int result = memberDao.insertMember(member);
		return result;
	}

	public List<Member> findAll() {
		List<Member> memberList = memberDao.findAll();
		return memberList;
	}

	public Member findById(String id) {
		Member member = memberDao.findById(id);
		return member;
	}
	
	public List<Member> findByName(String name) {
		return memberDao.findByName(name);
	}

	public int deleteMember(String id) {
		return memberDao.deleteMember(id);
	}
	
	public int updateMemberName(String id, String newName) {
		return memberDao.updateMemberName(id, newName);
	}

	public int updateMemberBirthday(String id, Date newBirthday) {
		return memberDao.updateMemberBirthday(id, newBirthday);
	}
	
	public int updateMemberEmail(String id, String newEmail) {
		return memberDao.updateMemberEmail(id, newEmail);
	}

	
}







