package com.sh.member.model.service;

// static 자원(메소드)를 import - 클래스명 생략가능 
import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Address;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	

	/**
     * DML
     * 1. driver class등록
     * 2. Connection 생성 & 자동커밋 false설정
     * 3. PreparedStatement 생성 & 미완성쿼리 값대입
     * 4. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
     * 5. 트랜잭션처리 
     * 6. 자원반납 
     * 
     * @param member
     * @return
     */
	public int insertMember(Member member) {
		int result = 0;
		// 1. Connection 가져오기
		Connection conn = getConnection();
		try {
			// 2. Dao 호출
			result = memberDao.insertMember(conn, member);
			List<Address> addressList = member.getAddressList();
			if(!addressList.isEmpty()) {
				Address address = addressList.get(0);
				result = memberDao.insertAddress(conn, address);
			}
			
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e; // controller에 예외사실 알림
		} finally {
			// 4. 자원반납
			close(conn);
		}
		return result;
	}

	public int updateMember(String id, String colName, Object newValue) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = memberDao.updateMember(conn, id, colName, newValue);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);			
		}
		return result;
	}

	/**
	 * DML
	 * 1. Connection 가져오기
	 * 2. dao호출
	 * 3. 트랜잭션처리 
	 * 4. 자원반납 
	 * 
	 * @param member
	 * @return
	 */
	public int deleteMember(String id) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.deleteMember(conn, id);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace(); // 로깅
		} finally {
			close(conn);
		}
		return result;
	}

	/**
     * DQL (n행 | 1행)
     * 1. driver class 등록
     * 2. Connection 생성
     * 3. PreparedStatement 생성 & 미완성쿼리 값대입
     * 4. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
     * 5. ResultSet 처리 : 행 -> dto객체
     * 6. 자원반납
     * 
     * @return
     */
	public List<Member> findAll() {
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. Dao 호출
		List<Member> memberList = memberDao.findAll(conn);
		// 3. 자원반납
		close(conn);
		return memberList;
	}

	public Member findById(String id) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn, id);
		// 조회된 member가 있을경우만 address 테이블 조회
		if(member != null) {
			List<Address> addressList = memberDao.findAddressByMemberId(conn, id); // address테이블만 조회
			member.setAddressList(addressList);			
		}
		close(conn);
		return member;
	}

	public List<Member> findByName(String name) {
		Connection conn = getConnection();
		List<Member> memberList = memberDao.findByName(conn, name);
		close(conn);
		return memberList;
	}
	
	public List<MemberDel> findAllMemberDel() {
		Connection conn = getConnection();
		List<MemberDel> memberList = memberDao.findAllMemberDel(conn);
		close(conn);
		return memberList;
	}

	public List<Address> findMemberAddressByMemberId(String id) {
		Connection conn = getConnection();
		List<Address> addressList = memberDao.findMemberAddressByMemberId(conn, id);
		close(conn);
		return addressList;
	}


}
