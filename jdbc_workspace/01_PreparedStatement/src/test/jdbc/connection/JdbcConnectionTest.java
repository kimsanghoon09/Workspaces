package test.jdbc.connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConnectionTest {
	
	private String driverClass = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속프로토콜@ip:port:sid
	private String user = "student";
	private String password = "student";
	

	public static void main(String[] args) {
		JdbcConnectionTest instance = new JdbcConnectionTest();
//		instance.test();
		instance.test2();
	}

	/**
	 * DML
	 */
	private void test2() {
		try {
			// 1. jdbc driver class 등록 (프로그램 1회)
			Class.forName(driverClass);
			// 2. Connection 생성 (url, user, password) - 자동커밋 false 설정
			try(Connection conn = DriverManager.getConnection(url, user, password)){
				conn.setAutoCommit(false); // 기본값 true
				// 3. PreparedStatement 생성
				String sql = "insert into member values (?, ?, ?, ?, ?, default, default)";
				try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
					// 미완성 쿼리 값대입
					pstmt.setString(1, "yoonbg");
					pstmt.setString(2, "윤봉길");
					pstmt.setString(3, "M");
					pstmt.setDate(4, Date.valueOf("1999-09-09"));
					pstmt.setString(5, "yoonbg@naver.com");
					
					// 4. 쿼리 실행 및 결과값(int) 받기
					int result = pstmt.executeUpdate();
					System.out.println(result + "행이 삽입되었습니다.");
					
					// 5. 트랜잭션처리		
					if(result > 0) conn.commit();
					else conn.rollback();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DQL
	 */
	private void test() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			// 1. jdbc driver class 등록 (프로그램당 최초 1회) - JVM에 해당 클래스 사용하겠다고 알림.
			Class.forName(driverClass);
			System.out.println("드라이버클래스 등록 완료!");
			
			// 2. Connection 생성 (url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection객체 생성 성공!");
			
			// 3. PreparedStatement 생성 (쿼리)
			String sql = "select * from member";
			pstmt = conn.prepareStatement(sql); 
			
			// 4. 쿼리실행 및 ResultSet 처리
			rset = pstmt.executeQuery();
			System.out.println("PreparedStatement 쿼리 실행완료!");
			
			while(rset.next()) {
				// 현재행 정보 가져오기
				String id = rset.getString("id");
				String name = rset.getString("name");
				Date birthday = rset.getDate("birthday");
				int point = rset.getInt("point");
				System.out.printf("%s	%s	%s	%s\n", id, name, birthday, point);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. 모든 자원 반환 (생성 역순)
			try {
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	}

}
