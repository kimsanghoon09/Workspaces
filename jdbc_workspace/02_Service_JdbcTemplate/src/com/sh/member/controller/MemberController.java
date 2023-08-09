package com.sh.member.controller;

import java.util.List;

import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public int insertMember(Member member) {
		int result = memberService.insertMember(member);
		return result;
	}

	public int updateMember(String id, String colName, Object newValue) {
		return memberService.updateMember(id, colName, newValue);
	}

	public int deleteMember(String id) {
		int result = memberService.deleteMember(id);
		return result;
	}

	public List<Member> findAll() {
		List<Member> memberList = memberService.findAll();
		return memberList;
	}

	public Member findById(String id) {
		return memberService.findById(id);
	}

	public List<Member> findByName(String name) {
		return memberService.findByName(name);
	}
	
	public List<MemberDel> findAllMemberDel() {
		return memberService.findAllMemberDel();
	}

}
