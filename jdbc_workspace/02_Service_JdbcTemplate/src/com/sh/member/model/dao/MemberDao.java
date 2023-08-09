package com.sh.member.model.dao;

import static com.sh.common.JdbcTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;


public class MemberDao {

	/**
	 * DML
	 * 1. PreparedStatement 생성 & 미완성쿼리 값대입
     * 2. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
     * 3. 자원반납 (PreapredStatement)
     * 
	 * @param conn
	 * @param member
	 * @return
	 * @throws SQLException 
	 */
	public int insertMember(Connection conn, Member member) throws SQLException {
		String sql = "insert into member values (?, ?, ?, ?, ?, default, default)";
		int result = 0;
		
		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			
			// 2. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// 예외가 발생하면 서비스로 던져서 예외발생여부를 알린다.
			throw e;
		}
		
		
		return result;
	}

	/**
	 * PreparedStatement는 컬럼명을 지정하는 set메소드를 지원하지 않는다.
	 * - String#replace를 통해 처리
	 * 
	 * @param conn
	 * @param id
	 * @param colName
	 * @param newValue
	 * @return
	 * @throws SQLException 
	 */
	public int updateMember(Connection conn, String id, String colName, Object newValue) throws SQLException {
		String sql = "update member set # = ? where id = ?";
		sql = sql.replace("#", colName);
		System.out.println(sql);
		int result = 0;
	
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setObject(1, newValue);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} 
	
		return result;
	}

	/**
	 * DML
	 * 1. PreparedStatement 생성 & 미완성쿼리 값대입
	 * 2. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
	 * 3. 자원반납 (PreapredStatement)
	 * 
	 * @param conn
	 * @param member
	 * @return
	 * @throws SQLException 
	 */
	public int deleteMember(Connection conn, String id) throws SQLException {
		String sql = "delete from member where id = ?";
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
		return result;
	}

	/**
	 * DQL
	 * 1. PreparedStatement 생성 & 미완성쿼리 값대입
     * 2. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
     * 3. ResultSet 처리 : 행 -> dto객체
     * 4. ResultSet, PreparedStatement 반환
     * 
	 * @param conn
	 * @return
	 */
	public List<Member> findAll(Connection conn) {
		List<Member> memberList = new ArrayList<>();
		String sql = "select * from member order by reg_date desc";
		
		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			// 2. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
			try(ResultSet rset = pstmt.executeQuery()){

				// 3. ResultSet 처리 : 행 -> dto객체
				while(rset.next()) {
					// 한행의 컬럼값을 가져와 Member객체로 변환 
					// 매개변수생성자 | 기본생성자 + setter
					Member member = handleMemberResultSet(rset);
					
					// memberList에 추가
					memberList.add(member);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return memberList;
	}

	public Member findById(Connection conn, String id) {
		Member member = null;
		String sql = "select * from member where id = ?";
	
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery();){
				if(rset.next())
					member = handleMemberResultSet(rset);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return member;
	}

	public List<Member> findByName(Connection conn, String name) {
		String sql = "select * from member where name like ?";
		List<Member> members = new ArrayList<>();
		
		// 1. PreparedStatment 생성 - 미완성쿼리 & 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");
			// 2. 실행 - ResultSet
			try(ResultSet rset = pstmt.executeQuery();) {
				// 3. ResultSet -> List<Member>
				while(rset.next()) {
					Member member = handleMemberResultSet(rset);
					members.add(member);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return members;
	}

	public List<MemberDel> findAllMemberDel(Connection conn) {
		List<MemberDel> members = new ArrayList<>();
		String sql = "select * from member_del order by del_date desc";

		try (
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
		) {
			
			while (rset.next()) {
				MemberDel member = handleMemberDelResultSet(rset);
				members.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return members;
	}

	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBirthday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setRegDate(rset.getTimestamp("reg_date"));
		return member;
	}

	private MemberDel handleMemberDelResultSet(ResultSet rset) throws SQLException {
		MemberDel member = new MemberDel();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBirthday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setPoint(rset.getInt("point"));
		member.setRegDate(rset.getTimestamp("reg_date"));
		member.setDelDate(rset.getTimestamp("del_date"));
		return member;
	}


}
