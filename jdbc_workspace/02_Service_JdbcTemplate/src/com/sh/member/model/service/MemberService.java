package com.sh.member.model.service;

// static 자원(메소드)를 import - 클래스명 생략가능 
import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.member.model.dao.MemberDao;
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
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
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
		close(conn);
		return member;
	}

	public List<Member> findByName(String name) {
		Connection conn = getConnection();
		List<Member> members = memberDao.findByName(conn, name);
		close(conn);
		return members;
	}
	
	public List<MemberDel> findAllMemberDel() {
		Connection conn = getConnection();
		List<MemberDel> members = memberDao.findAllMemberDel(conn);
		close(conn);
		return members;
	}


}
