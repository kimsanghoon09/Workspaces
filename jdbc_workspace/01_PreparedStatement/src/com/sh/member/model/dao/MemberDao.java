package com.sh.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.dto.Member;

/**
 * DAO Database Access Object
 * - db접근해서 쿼리요청을 보내고 결과를 처리하는 객체
 * 
 */
public class MemberDao {
	
	private String driverClass = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속프로토콜@ip:port:sid
	private String user = "student";
	private String password = "student";
	
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
		
		// 1. driver class등록
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 2. Connection 생성 & 자동커밋 false설정
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			conn.setAutoCommit(false);			
			
			// 3. PreparedStatement 생성
			String sql = "insert into member values (?, ?, ?, ?, ?, default, default)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// ? 값대입 - 인덱스는 1부터 시작
				pstmt.setString(1, member.getId());
				pstmt.setString(2, member.getName());
				pstmt.setString(3, member.getGender());
				pstmt.setDate(4, member.getBirthday());
				pstmt.setString(5, member.getEmail());
				
				// 4. 쿼리실행
				result = pstmt.executeUpdate();
				
				// 5. 트랜잭션처리
				if(result > 0) conn.commit();
				else conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		String sql = "select * from member order by reg_date desc";
		List<Member> memberList = new ArrayList<>();
		
		// 1. driver class 등록
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 2. Connection 생성
		// 3. PreparedStatement 생성
		// 4. 쿼리 실행 및 ResultSet 처리
		try (
			Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
		) {
			// 한행씩 접근, 한행마다 Member객체로 변환, memberList에 추가
			while(rset.next()) {
				String id = rset.getString("id"); // 컬럼명은 대소문자 구분하지 않음.
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp regDate = rset.getTimestamp("reg_date");
				
				Member member = new Member(id, name, gender, birthday, email, point, regDate);
				
				memberList.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return memberList;
	}

	
	public Member findById(String id) {
		String sql = "select * from member where id = ?";
		Member member = null;
		
		// 1. driver class 등록
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 2. Connection 생성
		// 3. PreparedStatement 생성 & 값대입
		try (
			Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, id);
			
			// 4. 쿼리실행 & ResultSet 처리
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					id = rset.getString("id");
					String name = rset.getString("name");
					String gender = rset.getString("gender");
					Date birthday = rset.getDate("birthday");
					String email = rset.getString("email");
					int point = rset.getInt("point");
					Timestamp regDate = rset.getTimestamp("reg_date");
					
					member = new Member(id, name, gender, birthday, email, point, regDate);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	public List<Member> findByName(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> members = new ArrayList<>();
		String sql = "select * from member where name like ?"; // '%길동%'
		
		
		try {
			// 1. jdbc driver클래스 등록
			Class.forName(driverClass);
			
			// 2. Connection객체 생성
			conn = DriverManager.getConnection(url, user, password);
			
			// 3. PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			
			// 4. 쿼리 실행, 결과 처리(ResultSet)
			rset = pstmt.executeQuery();
			
			// 5. ResultSet -> 자바객체(List<Member>)
			// rset.next():boolean 다음행 존재여부. 포인터를 다음행으로 이동
			while(rset.next()) {
				// 한행이 vo객체 하나와 매칭된다.
				String id = rset.getString("id");
				name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp regDate = rset.getTimestamp("reg_date");
				Member member = new Member(id, name, gender, birthday, email, point, regDate);
				members.add(member);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납 : 생성 역순
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return members;

	}


	public int deleteMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ?";
		int result = 0;
		
		try {
			// 1. driver class 등록(프로그램 실행시 1회) 
			// 클래스객체등록
			Class.forName(driverClass);
			
			// 2. Connection객체 생성(url, user, password) & auto-commit설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			// 3. PreparedStatement객체 생성(미완성쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4. 쿼리실행(DML: executeUpdate) & 결과값(int) 처리
			result = pstmt.executeUpdate();
			
			// 5. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납 : 생성 역순 반납
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	}
	
	public int updateMemberName(String id, String newName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set name = ? where id = ?"; // 세미콜론 찍지 말것.
		
		try {
			// 1. jdbc driver class 등록(프로그램 최초 1회 등록) 
			Class.forName(driverClass);
		
			// 2. Connection객체 생성(url, user, password)
			// autocommit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // application에서 직접 제어
			
			// 3. PreparedStatement객체 생성 : 사용자입력값 전달
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setString(2, id);
			
			// 4. 쿼리실행 및 결과 int 처리
			result = pstmt.executeUpdate(); // 처리된 행의 개수를 리턴
			
			// 5. 트랜잭션 처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납 : 객체생성의 역순으로 반환
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}

	

	public int updateMemberBirthday(String id, Date newBirthday) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set birthday = ? where id = ?"; // 세미콜론 찍지 말것.
		
		try {
			// 1. jdbc driver class 등록(프로그램 최초 1회 등록) 
			Class.forName(driverClass);
		
			// 2. Connection객체 생성(url, user, password)
			// autocommit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // application에서 직접 제어
			
			// 3. PreparedStatement객체 생성 : 사용자입력값 전달
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, newBirthday);
			pstmt.setString(2, id);
			
			// 4. 쿼리실행 및 결과 int 처리
			result = pstmt.executeUpdate(); // 처리된 행의 개수를 리턴
			
			// 5. 트랜잭션 처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납 : 객체생성의 역순으로 반환
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}

	public int updateMemberEmail(String id, String newEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update member set email = ? where id = ?"; // 세미콜론 찍지 말것.
		
		try {
			// 1. jdbc driver class 등록(프로그램 최초 1회 등록) 
			Class.forName(driverClass);
		
			// 2. Connection객체 생성(url, user, password)
			// autocommit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // application에서 직접 제어
			
			// 3. PreparedStatement객체 생성 : 사용자입력값 전달
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newEmail);
			pstmt.setString(2, id);
			
			// 4. 쿼리실행 및 결과 int 처리
			result = pstmt.executeUpdate(); // 처리된 행의 개수를 리턴
			
			// 5. 트랜잭션 처리
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 자원반납 : 객체생성의 역순으로 반환
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
	}


}
