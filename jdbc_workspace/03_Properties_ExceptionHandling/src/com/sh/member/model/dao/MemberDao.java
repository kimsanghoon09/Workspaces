package com.sh.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.member.model.dto.Address;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberDel;
import com.sh.member.model.exception.MemberException;

public class MemberDao {

	private Properties prop = new Properties();

	public MemberDao() {
		// resources/member-query.properties 파일을 읽어오기
		try {
			prop.load(new FileReader("resources/member-query.properties"));
			System.out.println("[member-query가 로드되었습니다. ] " + prop);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * DML 1. PreparedStatement 생성 & 미완성쿼리 값대입 2. 쿼리실행
	 * PreparedStatement#executeUpdate:int (처리된 행의 수) 3. 자원반납 (PreapredStatement)
	 * 
	 * @param conn
	 * @param member
	 * @return
	 * @throws SQLException
	 */
	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
		int result = 0;

		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());

			// 2. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// 예외가 발생하면 서비스로 던져서 예외발생여부를 알린다.
			// 발생한 예외를 새로운 예외(현재예외상황을 더 잘 설명할수 있는, 다루기 편한 Unchecked)로 전환해서 던진다.
			throw new MemberException("회원가입오류", e);
		}

		return result;
	}

	/**
	 * 한건의 address 레코드를 삽입하는 메소드 (주소록)
	 * 
	 * @param conn
	 * @param address
	 * @return
	 */
	public int insertAddress(Connection conn, Address address) {
		String sql = prop.getProperty("insertAddress");
		// insert into address (no, member_id, address) values(seq_address_no.nextval,
		// ?, ?)
		int result = 0;

		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, address.getMember().getId());
			pstmt.setString(2, address.getAddress());

			// 2. 쿼리실행 PreparedStatement#executeUpdate:int (처리된 행의 수)
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// 예외가 발생하면 서비스로 던져서 예외발생여부를 알린다.
			// 발생한 예외를 새로운 예외(현재예외상황을 더 잘 설명할수 있는, 다루기 편한 Unchecked)로 전환해서 던진다.
			throw new MemberException("주소록 등록 오류", e);
		}
		return result;
	}

	/**
	 * PreparedStatement는 컬럼명을 지정하는 set메소드를 지원하지 않는다. - String#replace를 통해 처리
	 * 
	 * @param conn
	 * @param id
	 * @param colName
	 * @param newValue
	 * @return
	 * @throws SQLException
	 */
	public int updateMember(Connection conn, String id, String colName, Object newValue) {
		String sql = prop.getProperty("updateMember");
		sql = sql.replace("#", colName);
		System.out.println(sql);
		int result = 0;

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setObject(1, newValue);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원정보 수정오류", e);
		}

		return result;
	}

	/**
	 * DML 1. PreparedStatement 생성 & 미완성쿼리 값대입 2. 쿼리실행
	 * PreparedStatement#executeUpdate:int (처리된 행의 수) 3. 자원반납 (PreapredStatement)
	 * 
	 * @param conn
	 * @param member
	 * @return
	 * @throws SQLException
	 */
	public int deleteMember(Connection conn, String id) {
		String sql = prop.getProperty("deleteMember");
		int result = 0;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원탈퇴 오류", e);
		}
		return result;
	}

	/**
	 * DQL 1. PreparedStatement 생성 & 미완성쿼리 값대입 2. 쿼리 실행
	 * PreapredStatement#executeQuery:ResultSet 3. ResultSet 처리 : 행 -> dto객체 4.
	 * ResultSet, PreparedStatement 반환
	 * 
	 * @param conn
	 * @return
	 */
	public List<Member> findAll(Connection conn) {
		List<Member> memberList = new ArrayList<>();
		String sql = prop.getProperty("findAll");

		// 1. PreparedStatement 생성 & 미완성쿼리 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 2. 쿼리 실행 PreapredStatement#executeQuery:ResultSet
			try (ResultSet rset = pstmt.executeQuery()) {

				// 3. ResultSet 처리 : 행 -> dto객체
				while (rset.next()) {
					// 한행의 컬럼값을 가져와 Member객체로 변환
					// 매개변수생성자 | 기본생성자 + setter
					Member member = handleMemberResultSet(rset);

					// memberList에 추가
					memberList.add(member);
				}
			}
		} catch (SQLException e) {
			throw new MemberException("전체 회원 조회 오류", e);
		}
		return memberList;
	}

	public Member findById(Connection conn, String id) {
		Member member = null;
		String sql = prop.getProperty("findById");

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rset = pstmt.executeQuery();) {
				if (rset.next())
					member = handleMemberResultSet(rset);
			}
		} catch (SQLException e) {
			throw new MemberException("회원 1명 조회 오류", e);
		}
		return member;
	}

	public List<Member> findByName(Connection conn, String name) {
		String sql = prop.getProperty("findByName");
		List<Member> members = new ArrayList<>();

		// 1. PreparedStatment 생성 - 미완성쿼리 & 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");
			// 2. 실행 - ResultSet
			try (ResultSet rset = pstmt.executeQuery();) {
				// 3. ResultSet -> List<Member>
				while (rset.next()) {
					Member member = handleMemberResultSet(rset);
					members.add(member);
				}
			}
		} catch (Exception e) {
			throw new MemberException("회원 이름 검색 오류", e);
		}
		return members;
	}

	public List<MemberDel> findAllMemberDel(Connection conn) {
		List<MemberDel> members = new ArrayList<>();
		String sql = prop.getProperty("findAllMemberDel");

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rset = pstmt.executeQuery();) {

			while (rset.next()) {
				MemberDel member = handleMemberDelResultSet(rset);
				members.add(member);
			}
		} catch (SQLException e) {
			throw new MemberException("탈퇴회원 조회 오류", e);
		}
		return members;
	}

	/**
	 * Address기준 (Member조회)
	 * 
	 * @param conn
	 * @param id
	 * @return
	 */
	public List<Address> findMemberAddressByMemberId(Connection conn, String id) {
		List<Address> addressList = new ArrayList<>();
		String sql = prop.getProperty("findMemberAddressByMemberId");
		// select * from member m join address a on m.id = a.member_id where m.id = ?

		// 1. PreparedStatement - ? 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			// 2. 쿼리실행
			try (ResultSet rset = pstmt.executeQuery()) {
				// 3. ResultSet처리 -> List<Address>
				while (rset.next()) {
					Address address = handleAddressResultSet(rset);
					Member member = handleMemberResultSet(rset);
					address.setMember(member);
					addressList.add(address);
				}
			}
		} catch (SQLException e) {
			throw new MemberException("회원별 주소록 조회 오류", e);
		}
		return addressList;
	}

	public List<Address> findAddressByMemberId(Connection conn, String id) {
		List<Address> addressList = new ArrayList<>();
		String sql = prop.getProperty("findAddressByMemberId");
		// select * from address where member_id = ?

		// 1. PreparedStatement - ? 값대입
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			// 2. 쿼리실행
			try (ResultSet rset = pstmt.executeQuery()) {
				// 3. ResultSet처리 -> List<Address>
				while (rset.next()) {
					Address address = handleAddressResultSet(rset);
					addressList.add(address);
				}
			}
		} catch (SQLException e) {
			throw new MemberException("주소록 조회 오류", e);
		}
		return addressList;
	}

	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBirthday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setPoint(rset.getInt("point"));
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

	private Address handleAddressResultSet(ResultSet rset) throws SQLException {
		Address address = new Address();
		address.setNo(rset.getInt("no"));
		address.setAddress(rset.getString("address"));
		address.setDefaultAddr(rset.getInt("default_addr") == 1);
		address.setRegDate(rset.getDate("reg_date"));
		return address;
	}

}
