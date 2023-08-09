package com.sh.member.controller;

import static com.sh.member.view.MemberMenu.displayError;
import java.util.List;

import com.sh.member.model.dto.Address;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();
	
	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		} catch (Exception e) {
			// 사용자에게 에러메세지 알림
			displayError(e.getMessage());
			// 로깅 (개발자용)
			e.printStackTrace();
		}
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
		List<Member> memberList = null;
		try {
			memberList = memberService.findAll();
		} catch (Exception e) {
			displayError(e.getMessage());
			e.printStackTrace();
		}
		return memberList;
	}

	public Member findById(String id) {
		Member member = null;
		try {
			member = memberService.findById(id);
		} catch (Exception e) {
			// 사용자에게 에러메세지 알림
			displayError(e.getMessage());
			// 로깅 (개발자용)
			e.printStackTrace();
		}
		return member; 
	}

	public List<Member> findByName(String name) {
		List<Member> memberList = null;
		try {
			memberList = memberService.findByName(name);
		} catch (Exception e) {
			// 사용자에게 에러메세지 알림
			displayError(e.getMessage());
			// 로깅 (개발자용)
			e.printStackTrace();
		}
		return memberList;
	}
	
	public List<MemberDel> findAllMemberDel() {
		List<MemberDel> memberList = null;
		try {
			memberList = memberService.findAllMemberDel();
		} catch (Exception e) {
			// 사용자에게 에러메세지 알림
			displayError(e.getMessage());
			// 로깅 (개발자용)
			e.printStackTrace();
		}
		return memberList;
	}

	public List<Address> findMemberAddressByMemberId(String id) {
		List<Address> addressList = null;
		try {
			addressList = memberService.findMemberAddressByMemberId(id);
		} catch (Exception e) {
			// 사용자에게 에러메세지 알림
			displayError(e.getMessage());
			// 로깅 (개발자용)
			e.printStackTrace();
		}
		return addressList;
	}

}
